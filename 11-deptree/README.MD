
### gradle依赖树-以树形结构查看相关依赖

主要依赖于如下两个命令 可以用示例的脚本试一下
由父寻子
```
gradle  dependencies   
```

由子寻父,需要指定寻找哪个子的父亲
```
gradle dependencyInsight  --dependency depName

```