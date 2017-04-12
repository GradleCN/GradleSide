# GradleSide
Gradle 示例大全

#### 构建的生命周期

![Gradle构建的生命周期(图片源于网络)](.\img\lifecyle.png)

如上图，Gradle构建过程中需要经历三个阶段: 初始化阶段 、 配直阶段 、 执行阶段

- 初始化阶段: 确定要参与构建的项目,创建配置Settings实例。
> 在构建的 初始化阶段，Gradle 会读取settings.gradle这个文件，并创建一个 Settings 类型的实例。Gradle 会依据此文件进行多模块项目构建，默认情况下 Gradle 会从同级的 master 目录下寻找此文件，如果未找到则会去父级目录寻找。如果搜寻不到 settings 文件，那么 Gradle 会把模块当做单独构建的项目去执行单独构建。

- 配置阶段: 确定task之间的依赖关系,并形成一张依赖关系图(有向无环图)决定task的执行顺序,这里需要注意的是 配置任务块时如果你的代码没有写到doFirst或者doLast里,那么代码会在配置阶段执行

- 执行阶段:	执行具体的task,gradle会依据配置阶段形成的关系图将任务链上的任务按依赖顺序依次执行

这里有个示例 展示个各个阶段以及各个阶段一些钩子函数的应用   

- [生命周期示例](https://github.com/GradleCN/GradleSide/tree/master/13-lifecycle)   
> [更多钩子函数请参考 - Gradle obj](https://docs.gradle.org/current/dsl/org.gradle.api.invocation.Gradle.html)   
> [更多钩子函数请参考 - Project obj](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html)   
   
   
   
   
   
_[参考资料 - Chapter 22. The Build Lifecycle] https://docs.gradle.org/current/userguide/build_lifecycle.html_