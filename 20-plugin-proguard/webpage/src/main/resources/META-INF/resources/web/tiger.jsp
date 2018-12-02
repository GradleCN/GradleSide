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
