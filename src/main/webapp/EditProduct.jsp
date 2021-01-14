<%@ page import="entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Value" %>
<%@ page import="entity.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Product</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/body.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
    <%
        Product product = (Product) request.getAttribute("product");
        List<Value> values = (List<Value>) request.getAttribute("values");
        List<Category> categories = (List<Category>) request.getAttribute("full_category");
    %>
    <form action="editproduct" method="post" autocomplete="off">
        <input type="hidden" name="product" value="<%= product.getId() %>">
        <p>
            Name product: (<%= product.getName() %>) <input type="text" name="pro_name"
                                                            placeholder="Input new name product...">
        </p>
        <p>
            Price product: (<%= product.getPrice() %>) <input min="1" type="number" name="pro_price"
                                                              placeholder="Input new price...">
        </p>
        <p>
            Full category path product:<br>
            <%
                for (Category category : categories) {
                    out.print(category.getCategory() + ">");
                }
            %>
        </p>
        <h3>Values:</h3>
        <%
            for (Value value : values) {
        %>
        <p>
            <%= value.getOption().getName() %>: (<%= value.getValue() %>) <input type="text" name="<%= value.getId() %>"
                                                                                 placeholder="Input value...">
        </p>
        <%
            }
        %>
        <input class="button" type="submit" value="Save">
    </form>
    <p>
        <button class="button" onclick="window.location.href = 'allproducts?for=edit'">Back</button>
    </p>
</div>
<jsp:include page="Footer.jsp"/>
</body>
</html>
