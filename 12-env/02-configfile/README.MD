# 通过配置文件支持多环境构建

　　除了通过传入参数加载不同目录下的properties文件来实现多环境打包之外,还有一种更便捷的方式来实现这种操作.    
借助Groovy的[ConfigSlurper](http://docs.groovy-lang.org/latest/html/gapi/groovy/util/ConfigSlurper.html#method_detail)特性可以简洁而明快的达到多环境打包的目的.打包时候仅需通过`-D`参数传入目标环境变量即可如:`gradle build -Denv=dev`,这里可以通过添加`gradle.properties`文件设置默认的环境变量值.

比如当前有如下需求:   

- 需要根据传入的变量参数进行不同环境打包
- 需要根据不同环境参数改变esources目录下属性文件\xml文件等文件的内容


1.与`build.gradle`平级建立`config.groovy`,这里的命名可以随意.

```groovy
environments {
    // 开发环境
    dev {
        db {
            username = "dev"
            password = 'devpwd'
        }        
    }
    // 线上环境
    production { 
        db {
            username = "prod"
            password = 'prodpwd'
        }        
    }
}
```
2.修改`build.gradle`

引入ReplaceToken
```groovy
import org.apache.tools.ant.filters.ReplaceTokens
```
处理资源文件时进行加载替换
```groovy
processResources {
    println "==> Load configuration for $env"
    def config =  new ConfigSlurper(env).parse(file("config.groovy").toURI().toURL()).toProperties()
    
    from(sourceSets.main.resources.srcDirs) {
       filesMatching('**/*.properties') {
        filter(ReplaceTokens, tokens: config, beginToken : '${', endToken : '}')
       }
    }
    
}

```   
　　默认情况下`ReplaceTokens`会将`@attribute@`的值替换成目标值,这里我们修改占位描述符为`${attribute}`

　　经过上面的操作,在执行打包命令时,`Gradle`会加载`config.groovy`文件中的配置对`src/main/resources`资源目录下的资源文件进行替换,注意这里替换的是所有资源文件(properties/xml/txt等)中的占位符,如果只想替换`properties`文件可以添加过滤限制来实现对部分文件内容的替换   

法1.
```groovy
  from(sourceSets.main.resources.srcDirs) {
       filesMatching('**/*.properties') {
        filter(ReplaceTokens, tokens: config, beginToken : '${', endToken : '}')
       }
    }
```
法2.
```groovy
  from(sourceSets.main.resources.srcDirs) {
        include '**/*.properties'       
        filter(ReplaceTokens, tokens: config, beginToken : '${', endToken : '}')
    }
````

完整代码在此:   
https://github.com/GradleCN/GradleSide/tree/master/12-env/02-configfile

