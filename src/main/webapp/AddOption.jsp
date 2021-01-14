<%@ page import="entity.Option" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/addOption.css" rel="stylesheet">
    <link href="css/body.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Header.jsp"/>
<%--<div class="header">
    <div class="img">
        <img src="images/1q2w3e.png">
    </div>
    <div class="right_head">
        Вы вошли как:
        <%
            if (session.getAttribute("id") != null) {
        %>
        <a href="userprofile"><%= session.getAttribute("name") %> (<%= session.getAttribute("login") %>)</a>
        <a href="exit">Log out</a>
        <%
        } else {
        %>
        Guest<a href="login">Log in</a>
        <%
            }
        %>
    </div>
</div>--%>
<div class="body">
    <%
        List<Option> options = (List<Option>) request.getAttribute("opt_for_category");
    %>
    <h3>Существующие опции:</h3>
    <div class="exists">
        <%
            for (Option option : options) {
                out.println("<div class=\"opt\">*" + option.getName() + "</div>");
            }
        %>
    </div>
    <form action="addnewoption" method="post" autocomplete="off">
        <h3 class="opt">Новые опции:</h3>
        <input type="hidden" name="category" value="<%= request.getParameter("select_category") %>">
        <input required type="text" name="options" placeholder="Input new option name...">
        <button id="add_opt">Add new option</button>
        <button type="submit">Save</button>
    </form>
    <div class="exists">
            <button class="button" onclick="window.location.href = 'test?for=addo'">Back</button>
            <button class="button" onclick="window.location.href = 'homepage'">To homepage</button>
    </div>
</div>
<jsp:include page="Footer.jsp"/>
<script src="scripts/addOption.js"></script>
</body>
</html>
