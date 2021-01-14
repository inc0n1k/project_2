<%@ page import="java.util.List" %>
<%@ page import="entity.Option" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product Options</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/body.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
    <%
        List<Option> options = (List<Option>) request.getAttribute("opt_for_category");
    %>
    <form action="addnewproduct" method="post" autocomplete="off">
        <input type="hidden" name="category" value="<%= options.get(0).getCategory().getId() %>">
        <table cellspacing="5">
            <tr>
                <td>
                    Input name product:
                </td>
                <td>
                    <input required type="text" name="name_prod">
                </td>
            </tr>

            <%
                for (Option option : options) {
            %>
            <tr>
                <td>
                    <%= option.getName()+":" %>
                </td>
                <td>
                    <input required type="text" name="<%=option.getId()%>">
                </td>
            </tr>
            <%
                }
            %>

            <tr>
                <td>
                    Input price product:
                </td>
                <td>
                    <input required type="number" min="1" name="price_prod">
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input class="button" type="submit" value="Create">
                </td>
            </tr>
        </table>
    </form>
    <p>
        <button onclick="window.location.href='test?for=addp'">Back</button>
    </p>
</div>
<jsp:include page="Footer.jsp"/>
</body>
</html>
