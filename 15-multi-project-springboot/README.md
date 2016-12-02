####  多模块项目之 -  spring boot + gradle + 构建公共jsp页面的多模块项目

标题党!!!!

#### 烹制原理  
Servlet 3.1 规范里 jar包中`META-INF/resources/`下的文件会被视为根目录文件进行正常访问 , 这也就意味着我们可以将某些 静态页面/图片/样式/js 等打到jar包里当做依赖jar包引用 , 像`webjars`基本就是这么搞的.当然 如果你在搞前后端分离、gulp、webpack什么的这种方式基本也不用考虑了。   

Servlet 3.1 中文译本 张开涛 翻译版下载 [> 点传送门 <](http://jinnianshilongnian.iteye.com/blog/1912455)

- 引用.1
>除了 静态资源和 WEB-INF/lib 目录下打包在 JAR 文件中 META-INF/resources 目录下的 JSP 文件之外，WEB-INF 目录下包含的其他任何文件都不能由容器直接提供给客户端访问。	  
> serverlet 3.1 规范 - 10.5   
    
- 引用.2
>4. 添加了从 JAR 文件中加载静态资源和 JSP 的支持，其包含在绑定在 WEB-INF/lib 目录中的 JAR 文件的 META-INF/resources 目录中   
>变更历史 A.3   


#### 烹制方法

1. 添加spring boot 依赖,如果不想用变量定义版本号可以直接撸版本号
```groovy
    compile "org.springframework.boot:spring-boot-starter-web:${bootVersion}",
            "org.apache.tomcat.embed:tomcat-embed-jasper:${tomcat_embed}"
```

2. 编写controller
```java
@Controller
@Slf4j
public class TigerCtrl {
    @RequestMapping("/")
    public ModelAndView tiger(){
        log.info("Soft kitty,warm kitty Little ball of fur .");
        return new ModelAndView("tiger","words","Roar ~ Roar ~ ");
    }
}
```

3. 编写页面
```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<img alt="j2ee" src="static/duke.jpg" width="190" height="253"/><br/>
<br/>
<%
    String words = String.valueOf(request.getAttribute("words"));
    out.println("Tiger said : " + words);
%>
</body>
</html>
```

4. 添加公共jsp页面模块依赖
```groovy
  compile    project(":webpage")
```

#### 完整代码

[ > 点击这里看完整代码 < ](https://github.com/pkaq/GradleSide/tree/master/15-multi-project-springboot)
