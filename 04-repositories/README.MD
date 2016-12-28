
### Gradle仓库管理

各种仓库
本地缓存
中央仓库
jcenter
maven私服
带权限的maven私服
文件夹
ivy仓库
一坨   

需要注意的是：如果使用maven本地缓存的话，在maven配置了环境的情况下，由maven安装目录中配置的setting.xml（localRepository节点）中指定，没有的话默认从用户目录的.m2中读取

[最后更新日期:2016/09/23]:下面是收集的一些常用库
```groovy
//使用Maven本地缓存
mavenLocal()

//使用jcenter
jcenter()

//使用中央仓库
mavenCentral()

//使用开源中国的maven库,此库经常间歇性地域性歇菜
maven { 
	url "http://maven.oschina.net/content/groups/public/" 
	name = "开源中国的maven库"	
}

//使用spring的maven库,name是可选的 我也可以不指定name
maven { url"https://repo.spring.io/libs-release" }


maven {
	url "http://maven.aliyun.com/nexus/content/groups/public/"
    name = "阿里云"
}

maven {
	url "http://uk.maven.org/maven2/"
    name = "maven uk"
}

maven {
	url "http://mirrors.ibiblio.org/maven2/"
	name = "中国软件开发网"
}

maven {
	url "http://repository.jboss.org/nexus/content/groups/public/"
	name = "jboss"
}

maven {
	url "https://repository.jboss.org/nexus/content/repositories/releases/"
	name = "jbossJBPM"
}

maven {
	url "http://maven.antelink.com/content/repositories/central/"
	name = "antepedia"
}

maven {
	url "http://mavensync.zkoss.org/maven2/"
	name = "zkoss"
}

```