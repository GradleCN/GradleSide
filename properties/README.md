这是一个使用gradle操作Properties文件的示例
本示例代码已在gradle 2.8 2.9版本运行通过

主要包括了properties文件的 读取、修改等操作  

1.gradle rP 读取配置文件属性  
2.gradle uP 更改配置文件内容  
3.gradle bP -Pkeyp=imkp  读取 -P 参数传入的属性  
4.gradle bD -Dkeyd=imkd  读取 -D参数传入的属性  

更多用法详询PropertiesAPI  

-D属性会被传送给启动Gradle的jvm，作为一个系统属性被jvm使用。  
-P属性则会被直接加载到Gradle领域对象上   

####Gradle支持三种Properties
####System Properties：
1.可通过gradle.properties文件，环境变量或命令行-D参数设置
2.可在setting.gradle或build.gradle中动态修改，在setting.gradle中的修改对buildscript block可见；
3.所有工程可见，不建议在build.gradle中修改
4.多子工程项目中，子工程的gradle.properties会被忽略掉，只有root工程的gradle.properties有效


####Project Properties：
1.可通过gradle.properties文件，环境变量或命令行-P参数设置，优先级是:  
2.可在build.gradle中动态修改，但引用不存在的project properties会立即抛错  
3.动态修改过的project properties在buildscript block中不可见  


####Project ext properties：
1.可在项目的build.gradle中声明和使用，本工程和子工程可见  
2.不能在setting.gradle中访问  


如果有多处设置，加载次序如下,后面的覆盖前面的设置
1.from gradle.properties located in project build dir.
2.from gradle.properties located in gradle user home.
3.from system properties, e.g. when -Dsome.property is used in the command line.
4.setting.gradle
5.build.gradle

