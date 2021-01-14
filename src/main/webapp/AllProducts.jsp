<%@ page import="entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Value" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/body.css" rel="stylesheet">
    <style>
        .prod_for_buy {
            display: flex;
            flex-direction: row;
        }

        .buy_but {
            width: 50px;
            margin-left: 5px;
        }
    </style>
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
    <%
        String act;
        switch (request.getParameter("for")) {
            case "edit":
                act = "allproducts";
                break;
            case "buy":
                act = "buy";
                break;
            default:
                response.sendRedirect("HomePage.jsp");
                return;
        }
//        Map<Product, List<Value>> productMap = (Map<Product, List<Value>>) request.getAttribute("all_p_v");
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (act.equals("allproducts") && products.size() != 0) {
    %>
    <form action="<%= act %>" method="post" autocomplete="off">
        <%
            }
//            for (Map.Entry<Product, List<Value>> entry : productMap.entrySet()) {
            for (Product product : products) {
        %>
        <div class="prod_for_buy">
            <%
                if (act.equals("allproducts")) {
            %>
            <%--            <input required type="radio" name="edit_product" value="<%= entry.getKey().getId() %>">--%>
            <input required type="radio" name="edit_product" value="<%= product.getId() %>">
            <%
                }
            %>
            <%--            Name: <%=entry.getKey().getName()%>--%>
            Name: <%= product.getName() %>
            <%
                if (act.equals("buy")) {
            %>
            <%--            <button class="buy_but" value="<%= entry.getKey().getId() %>" onclick="addProductToCart(this)">Add--%>
            <button class="buy_but" value="<%= product.getId() %>" onclick="addProductToCart(this)">Add
            </button>
            <%
                }
            %>
        </div>
        <details>
            <summary>Details</summary>
            <%--            Category: <%= entry.getKey().getCategory().getCategory()%><br>--%>
            Category: <%= product.getCategory().getCategory()%><br>
            <%
                //                for (Value value : entry.getValue()) {
                for (Value value : product.getValues()) {
            %>
            >>>>>> <%= value.getOption().getName()%>:<%=value.getValue()%><br>
            <%
                }
            %>
            <%--            Price: <%=entry.getKey().getPrice()%>--%>
            Price: <%=product.getPrice()%>
        </details>
        <%
            }
            if (act.equals("allproducts") && products.size() != 0) {
        %>
        <input class="button" type="submit" value="Edit">
    </form>
    <%
        }
    %>
    <p>
        <button class="button" onclick="window.location.href = 'test?for=<%= request.getParameter("for") %>'">Back
        </button>
    </p>
    <p>
        <button class="button" onclick="window.location.href = 'homepage'">To homepage</button>
    </p>
</div>
<jsp:include page="Footer.jsp"/>
<script src="scripts/addToCart.js"></script>
</body>
</html>
