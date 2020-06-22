####  多模块项目之 -  spring boot + gradle + 集中管理依赖  

借助`Gradle`脚本的`ext`扩展属性, 可以将所有模块的依赖进行集中管理, 原理就是通过在`ext`块中定义版本号并且维护一个对应的`map`,在子模块应用的时候可以直接通过引用该`map`的key值来获取依赖   

```groovy
ext {
    springBootVersion = '2.3.1.RELEASE'
    lombok = "1.18.12"

    lib = ["spring" : "org.springframework.boot:spring-boot-starter-web:${springBootVersion}",
		   // lombok
           "lombok" : "org.projectlombok:lombok:${lombok}"            
    ]
}
```

子模块中可以直接通过key值来引用相应的依赖
```groovy
dependencies {
    compile "${lib.spring}"

    compile project(":webpage")
}
```