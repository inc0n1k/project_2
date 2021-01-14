<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/body.css" rel="stylesheet">
    <link href="css/homePage.css" rel="stylesheet">
</head>
<body>
<%
    User user = null;
    if (session.getAttribute("id") != null) {
        user = (User) request.getAttribute("user");
    }
%>
<jsp:include page="Header.jsp"/>
<div class="body">
    <div class="general">
        <div class="item">
            <a href="test?for=buy">Buy products</a>
        </div>
        <%
            if ((user != null) && !(user.getRole().getRole().equals("Пользователь"))) {
        %>
        <div class="item">
            <a href="test?for=edit">Edit products</a>
        </div>
        <div class="item">
            <a href="test?for=addp">Add Product</a>
        </div>
        <div class="item">
            <a href="test?for=addo">Add option</a>
        </div>
        <div class="item">
            <a href="allorders">All active orders</a>
        </div>
        <%
            }
            if (user != null) {
        %>
        <div class="item">
            <a href="userorder?id=<%= session.getAttribute("id") %>">All user orders</a>
        </div>
        <%
            }
        %>
    </div>
</div>
<jsp:include page="Footer.jsp"/>
</body>
</html>
