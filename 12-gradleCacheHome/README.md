####4 Gradle指定/修改缓存目录



可以
法1.通过添加系统变量 GRADLE_USER_HOME  
法2.设置虚拟机参数 org.gradle.user.home 属性   
法3.通过命令行-g或者 --gradle-user-home 参数设置   

Note, «GRADLE_USER_HOME» defaults to «USER_HOME»/.gradle, where «USER_HOME» is the home directory of the current user. This location can be configured via the -g and --gradle-user-home command line switches, as well as by the GRADLE_USER_HOME environment variable and org.gradle.user.home JVM system property.

示例援引官方userguide第18.2章节(ver2.9)