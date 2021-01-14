<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<%
    User user = (User) request.getAttribute("user");
%>
<p>
    Name: <%= user.getName() %>
</p>
<p>
    Login: <%= user.getLogin() %>
</p>
</body>
</html>
