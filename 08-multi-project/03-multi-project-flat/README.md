# GradleSide
Gradle 示例大全

16.flat-multi-project : gradle多项目示例

一个简单的水平布局的多项目示例
main依赖base

心法在于两点

1. 根项目与被依赖的项目(在水平意义上的子项目,视觉上根项目的兄弟项目)保持平级。
2. 配置根项目的 `setting.gradle` ， 采用 `includeFlat` 来描述子项目路径(由于采用的是水平布局,默认根路径就是当前根项目的上级路径,所以无需用../上跳)
3. 观摩示例