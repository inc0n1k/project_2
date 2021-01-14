<%@ page import="java.util.List" %>
<%@ page import="entity.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select Category</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/body.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
    <%
        List<List<Category>> allFullCategories = (List<List<Category>>) request.getAttribute("allFullCategories");
    %>
    <h2>Select category:</h2>
    <form action="selectcategory" method="post" autocomplete="off">
        <input type="hidden" name="for" value="<%= request.getParameter("for") %>">
        <%
            for (List<Category> categoryList : allFullCategories) {
        %>

        <p>
            <input required type="radio" name="select_category"
                <%
            StringBuilder full_cat = new StringBuilder();
           for (int i = 0;i < categoryList.size() - 1; i++){
                full_cat.append(categoryList.get(i).getCategory()).append(">");
            }
//            }
        %>
                   value="<%= categoryList.get(categoryList.size() - 1).getId() %>">
            <span title="<%= full_cat.toString() %>">
        <%= categoryList.get(categoryList.size() - 1).getCategory() %>
    </span>
        </p>
        <%
            }
        %>
        <input class="button" type="submit" value="Next">
    </form>
    <p>
        <button class="button" onclick="window.location.href = 'homepage'">To homepage</button>
    </p>
</div>
<jsp:include page="Footer.jsp"/>
</body>
</html>
