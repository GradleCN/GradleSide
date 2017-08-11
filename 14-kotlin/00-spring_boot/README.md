Hello Kotlin~

`Kotlin`正式发布之后吸引了不少目光,就连`Gradle`在3.5版本的时候都推出了基于`Gradle`的dsl.
上有亲爹`jetbrains`,下有干爹`google`,真是想没人关注都难.

下面展示了如何用`Gradle`来构建一个基于`Kotlin`的`Srping Boot`项目.
> p.s 好吧,我承认加spring boot纯粹是为了博眼球蹭热度的

1.项目结构
src
    |-main
        |-kotlin

这里放在`src/main/java`下也是可以的

2.构建脚本

```groovy
// 应用kotlin插件
plugins {
    id "org.jetbrains.kotlin.jvm" version "1.1.3-2"
}
// 定义版本号
ext{
    kotlin_version = '1.1.2-4'
}
// 定义仓库
repositories {
    mavenLocal()
    maven { url"https://repo.spring.io/libs-release" }
    jcenter()
    mavenCentral()
}

dependencies {
    // kotlin标准库支持
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    // 编译成java8规范字节码
    compile "org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlin_version"
    // ...其它依赖

}
```