# 使用proguard混淆你的spring boot应用



# Proguard介绍

　　安卓开发的同学想必对`Proguard`都是十分熟悉的，由于java的反编译实在是太容易，使用它可以对`java`源码进行混淆处理，这样即使反编译之后也极大的降低了源码的可读性。当然除了混淆，`proguard`还有其余一些功能。  

- shrink： 检测并移除没有用到的类，变量，方法和属性；   

- optimize: 优化代码，非入口节点类会加上`private`/`static`/`final`, 没有用到的参数会被删除，一些方法可能-会变成内联代码。  

- obfuscate: 使用短又没有语义的名字重命名非入口类的类名，变量名，方法名。入口类的名字保持不变。  

- preverify: 预校验代码是否符合Java1.6或者更高的规范(唯一一个与入口类不相关的步骤)   

 

![proguard](./assets/proguard.png)

# 环境准备

- proguard plugin: 6.0.3 

- spring boot: 2.0.3

- gradle 4.9

- windows 10

  

# 引言

　　普通`proguard`的使用方式非常简单，只要下载`proguard.jar`直接运行`java -jar proguard.jar @progurad.pro`即可。@后面指定的是规则文件。本篇着重讲解如何通过`gradle task`的方式来进行混淆打包。   

　　这里以`spring boot`为例，当然这并非仅限于`spring boot`应用，只要明白了原理，所有`java`应用都可以如法炮制。最重要的是如何编制规则文件，需要特别注意的是用到反射的类不要进行混淆。   

　　有人说后端java应用部署在服务器端，对代码进行混淆并没有太大意义，关于此点不在本文讨论范围之内，本文仅提供纯技术层面的探讨，喜者自取，恶者轻喷。

　　

# 步骤

引入方式有两种,第一种是自行下载后采用本地引用，第二种只直接去中央仓库加载依赖。 这里采用`法二`的方式

法一：

```groovy
buildscript {
    repositories {
        flatDir dirs: '/usr/local/java/proguard/lib'
    }
    dependencies {
        classpath ':proguard:'
    }
}
```

法二：

```groovy
buildscript {
	repositories {
	    mavenCentral()
	}

	dependencies {
	    classpath 'net.sf.proguard:proguard-gradle:6.0.3'
	}
}
```



　　具体做法比较简单，这里直接贴出完整构建文件如下   

 　　`spring boot plugin`的`bootjar`任务默认会打包所有`runtime` 期间的依赖以及`$builddir\classes`下的class文件，这里为了让`spring boot`从混淆后的class中打包，所以自定义了`bootPro`任务，该任务为`bootJar`类型任务（这里可以理解为继承）同时依赖于`proguard`任务，对`bootInf`方法进行了重写。如此可以在代码混淆后将混淆后的`class`文件打包到`jar`包中。   

　　需要说明的是`Proguard`任务内部可以直接编写混淆规则，也可以引用外部规则文件，这里采用的是引用外部文件的方法。 具体规则可以参考文章尾部的`proguard`链接。  

```groovy
buildscript {
	repositories {
	    mavenCentral()
	}

	dependencies {
	    classpath 'net.sf.proguard:proguard-gradle:6.0.3'
	}
}
plugins {
    id 'org.springframework.boot' version '2.0.3.RELEASE'
}

import proguard.gradle.ProGuardTask
import org.springframework.boot.gradle.tasks.bundling.BootJar

apply plugin: 'io.spring.dependency-management'

dependencies {
    compile "org.springframework.boot:spring-boot-starter-web:${bootVersion}",
            "org.apache.tomcat.embed:tomcat-embed-jasper:${tomcat_embed}"
    compileOnly "org.projectlombok:lombok:1.16.10"

    compile    project(":webpage")
}

// 不混淆打普通jar
bootJar {
  mainClassName = 'org.pkaq.Booter'
}
// 混淆任务
task proguard(type: ProGuardTask, dependsOn: compileJava) {
	// 输出混淆前->混淆后的映射
    printmapping "$buildDir/mapping.txt"
    // 混淆规则文件
    configuration 'proguard-rules.pro'

    // 混淆时依赖的库
    libraryjars files(configurations.compile.findAll {}.collect())
    libraryjars "${System.getProperty('java.home')}/lib/rt.jar"
    libraryjars "${System.getProperty('java.home')}/lib/jce.jar"

    // 混淆输入
    injars sourceSets.main.output
    // 混淆输出
    outjars "$buildDir/classes-pro"
}

// 混淆打包
task bootPro(type: BootJar){
  dependsOn 'proguard'
  // 重新组织boot-inf下的文件
  bootInf {
    into('classes') {
        from "$buildDir/classes-pro"
    }

    into('lib') {
        from configurations.runtime
    }
  }
  // 包名
  baseName = "web"
  // 入口
  mainClassName = 'org.pkaq.Booter'
  // 删除混淆后的文件 避免下次混淆打包报错
  doLast {
      new File("$buildDir/classes-pro").deleteDir()
  }
}
```



- [本文示例源码](https://github.com/GradleCN/GradleSide/tree/master/19-plugin-proguard) 
- [proguard](https://www.guardsquare.com/en/products/proguard/manual/gradle)
- [Spring boot plugin API](https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/gradle-plugin/api/)
- [Spring boot plugin BootJar doc](https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/gradle-plugin/reference/html/)