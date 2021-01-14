<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
<%
    if (session.getAttribute("id") != null) {
        response.sendRedirect("homepage");
        return;
    }
%>
<div class="content_box">
    <form class="login_form" action="login" method="post" autocomplete="off">
        <%
            if (session.getAttribute("login_error") != null) {
        %>
        <div class="error">
            <%= session.getAttribute("login_error")%>
        </div>
        <%
                session.removeAttribute("login_error");
            }
        %>
        <input autofocus class="input" required placeholder="Input login..." type="text" name="login">
        <input class="input" required placeholder="Input password..." type="password" name="pass">
        <div class="button_form">
            <div>
                <input class="button" type="submit" value="Login">
            </div>
            <div>
                <button class="button" onclick="window.location.href='homepage'"> Login as guest</button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="Footer.jsp"/>
</body>
</html>