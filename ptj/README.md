这是一个使用gradle上传到Jcenter的一个示例
具体步骤 这里写的已经非常明白了  并且提供了多个示例
https://github.com/bintray/gradle-bintray-plugin


-------
上传到jcenter至少需要四个文件，除了打包的jar之外，
还需要pom和javadoc，source，否则是通不过jcenter审核的。

-------

下面是博客引文
有时你或许会想发布自己的项目到jcenter来供大家采用gav的形式引用
辣么接下来就是告诉你如何发布自己的项目到jcenter
什么是 Jcenter ：Forget about Maven Central


####版本信息
JDK : 1.8
Gradle  : 2.9


####1、前期准备
  首先你得去[Bintray官方网站](https://bintray.com/)去注册一个账号
  注册完成后点击编辑Profile获取你的API key
####2、编写脚本


因为正常情况下build.gradle还要做一些其它事情，而这里上传的脚本又比较长所以独立出来。

######build.gradle

Gradle 2.1+写法：
```groovy
plugins {
    id "com.jfrog.bintray" version "1.4"
}
//你的bintray.gradle的相对路径
apply from: 'binary.gradle'
```

Gradle 2.1-写法：
```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.4'
    }
}
//你的bintray.gradle的相对路径
apply from: 'binary.gradle'
```
gradle.properties
```groovy
#JCENTER用户名和API key
BINTRAY_USER=<YOUR USERNAME>
BINTRAY_APIKEY=<YOUR APIKEY>

#GAV
GROUP=cn.pkaq
ARTIFACT_ID=ptj.tiger
VERSION_NAME=0.1.0

#项目地址相关
POM_URL=http://pkaq.github.io
POM_SCM_URL=https://github.com/pkaq/GradleSide
POM_SCM_CONNECTION=git@github.com:pkaq/GradleSide.git
POM_SCM_DEV_CONNECTION=git@github.com:pkaq/GradleSide.git
#项目相关信息
GIT_URL=https://github.com/pkaq/GradleSide.git
ISSUE_URL=https://github.com/pkaq/GradleSide/issues

POM_PACKAGING=jar
POM_DESCRIPTION=Tiger say hi~

#开源协议
POM_LICENCE_NAME=The Apache Software License, Version 2.0
POM_LICENCE_URL=http://www.apache.org/licenses/LICENSE-2.0.txt
POM_LICENCE_DIST=repo

#开发者信息
POM_DEVELOPER_ID=pkaq
POM_DEVELOPER_NAME=Frank.W
POM_DEVELOPER_EMAIL=pkaq@msn.com
POM_DEVELOPER_URL=http://pkaq.github.io


```

######binary.gradle
```groovy
//上传到jcenter至少需要四个文件，除了打包的jar之外，
//还需要pom和javadoc，source，否则是通不过jcenter审核的。
apply plugin: 'maven-publish'
apply plugin: 'com.jfrog.bintray'
apply plugin: 'java'

group = GROUP
version =  VERSION_NAME

def getBintrayUserProperty() {
    return hasProperty('BINTRAY_USER') ? BINTRAY_USER : ""
}

def getBintrayApiKeyProperty() {
    return hasProperty('BINTRAY_APIKEY') ? BINTRAY_APIKEY : ""
}

//根据jcenter的审核要求 必须包含sourceJar和docJar
task sourceJar(type: Jar) {
    from sourceSets.main.allSource
    classifier = 'sources'
}

task javadocJar(type: Jar, dependsOn: javadoc) {
    classifier = 'javadoc'
    from javadoc.destinationDir
}
publishing {
    publications {
        mavenJava(MavenPublication) {
            //GAV配置
            groupId GROUP
            artifactId ARTIFACT_ID
            version VERSION_NAME

            from components.java
            artifact sourceJar
            artifact javadocJar

            //pom相关信息
            pom.withXml {
                Node root = asNode()
                root.appendNode('name', ARTIFACT_ID)
                root.appendNode('description', POM_DESCRIPTION)
                root.appendNode('url', POM_URL)

                def issues = root.appendNode('issueManagement')
                issues.appendNode('system', 'github')
                issues.appendNode('url', ISSUE_URL)

                def scm = root.appendNode('scm')
                scm.appendNode('url', POM_SCM_URL)
                scm.appendNode('connection', POM_SCM_CONNECTION)
                scm.appendNode('developerConnection', POM_SCM_DEV_CONNECTION)

                def license = root.appendNode('licenses').appendNode('license')
                license.appendNode('name', POM_LICENCE_NAME)
                license.appendNode('url', POM_LICENCE_URL)
                license.appendNode('distribution', POM_LICENCE_DIST)
            }
        }
    }
}
// gradle bintrayUpload
bintray {
    user = getBintrayUserProperty()
    key = getBintrayApiKeyProperty()
    publications = ['mavenJava']
//    configurations = ['archives']
    dryRun = false
    publish = true
    pkg {
        //你在bintray建立的仓库类型
        repo = 'maven'
        //包名
        name = ARTIFACT_ID
        //描述
        desc = POM_DESCRIPTION
        //站点地址
        websiteUrl = POM_URL
        //缺陷提交地址
        issueTrackerUrl = ISSUE_URL
        //版本库地址
        vcsUrl = GIT_URL
        //许可证
        licenses = ['Apache-2.0']
        labels = ['gradle', 'tiger']
        publicDownloadNumbers = true

        version {
            //版本号
            name = VERSION_NAME
            //版本描述
            desc = POM_DESCRIPTION
        }
    }
}
```
####3、提交待审
调用bintrayUpload上传到Jcenter
```shell
	gradle -q bU
```

当bintrayUpload成功之后，

在我的主页https://bintray.com/{your_username}右下部分Latest Activity块，会看到你提交了一个项目，
从这个Latest Activity列表中点击你的项目，
进入详情页https://bintray.com/{your_username}/maven/lesscode-core/view，
找到Maven Central标签，
鼠标放上去它会提示你去提交审核，"To be synced to Maven Central your package needs to be included in the JCenter repository. Click here to get it included."
点击进入后，随便写什么都可以了。
过审之后在Linked to下面就可以看到多了个Jcenter xxx了


P.S:bintray所有的删除操作都是在edit界面里。