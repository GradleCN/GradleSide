# GradleSide
### Gradle 示例大全

广告 如果你喜欢gradle，我们有一个群 109752483

示例的内容对你而言或许过于基础，或许觉得冷门，但。。。   
这些问题撸主都曾经真真的回答过遇到过   
 
 **00.00**  潜规则   
 
**00.peer**  peer not authenticated 错误的究极解决之道     

**03.buildscript**  
- 01 base 一个脚本结构示例  
- 02 extdepend  引入外部依赖进行构建
- 
**04.repositories**   关于仓库的使用，多个仓库，自定义仓库，本地仓库...     

**05.dependencies**  依赖管理相关示例    
- 01 depbase 最简单的依赖管理示例   
- 02 local-dependency 使用本地文件系统依赖   
- 03 ptj  如何使用Gradle发布自己的项目到Jcenter   
- 04 deptree  gradle依赖树-以树形结构查看相关依赖  
- 05 gradleCacheHome  更改gradle的缓存目录   
- 06 moduleVersion 使用版本号变量以及使用动态版本   
- 07 transitive   关于依赖传递，如何取消依赖传递以及如何排除个别依赖   
- 08 clientModule 操作依赖的依赖   

**06.task**  gradle中的任务
- 01 task 一个简单的配置型任务    
- 02 action 一个简单的动作型任务   
- 03 depends  任务依赖   
- 04 skip  任务跳过  
- 05 dynamic  动态任务   
- 06 inbuild 类型任务   
- 07 customTask   自定义任务   


**07.properties**  使用gradle操作properties    

**08.sourcesets**  自定义项目目录结构    

**09.multi-project**   
- 01 multi-project gradle多项目示例    
- 02 multi-project-springboot 结合spring boot构建采用公共jsp页面的多模块项目     
- 03 multi-project-flat 用水平布局的多模块(项目)项目构建    
- 04 multi-project-central 采用集中化配置的多模块(项目)项目构建,同时包括在settings文件中添加自定义代码    
  
**10.unicode**  各种乱码问题，中文注释编译报错，中文文件名编译报错，javadoc乱码等    

**11.resources** 打包src/main/java下的资源文件 

**12.specifyName**   打指定名称和版本号的jar包    

**13.env** 根据不同环境打包web项目      

**14.lifecycle** gradle构建的生命周期    

**15.kotlin**   
- 使用Gradle打包基于kotlin的spring boot项目  

**16.plugin-java**   
- 00 fatjar - 打可执行jar包  

**17.plugin-checkstyle** 代码风格检查    

**18.plugin-pmd**   
- 应用pmd插件使用阿里开发规范检查代码

**19.plugin-jib**容器化你的Gradle Java项目,jib快速入门

**20.plugin-proguard**使用proguard混淆你的sb应用
