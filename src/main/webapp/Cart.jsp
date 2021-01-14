<%@ page import="java.util.List" %>
<%@ page import="entity.Product" %>
<%@ page import="entity.Value" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Basket</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link href="css/body.css" rel="stylesheet">
    <style>
        .count {
            width: 75px;
        }
    </style>
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
    <table width="1000" id="ttt" border="2">
        <tr>
            <th colspan="2">Name</th>
            <th>Category</th>
            <th>Options</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Sum</th>
        </tr>
        <%
            //            Map<Product, List<Value>> productMap = (Map<Product, List<Value>>) request.getAttribute("all_p_v");
            List<Product> products = (List<Product>) request.getAttribute("products");
//            for (Map.Entry<Product, List<Value>> entry : productMap.entrySet()) {
            for (Product product : products) {
        %>
        <tr>
            <td align="center">
                <%--                <input type="hidden" value="<%= entry.getKey().getId() %>">--%>
                <input type="hidden" value="<%= product.getId() %>">
                <button onclick="buttonDel(this)">Del</button>
            </td>
            <%--            <td><%= entry.getKey().getName()%>--%>
            <td><%= product.getName()%>
            </td>
            <%--            <td><%= entry.getKey().getCategory().getCategory() %>--%>
            <td><%= product.getCategory().getCategory() %>
            </td>
            <td>

                <%
                    //for (Value value : entry.getValue()) {
                    for (Value value : product.getValues()) {
                        out.print(value.getOption().getName() + " : ");
                        out.print(value.getValue() + "<br>");
                    }
                %>

            </td>
            <%--            <td><%= entry.getKey().getPrice() %>--%>
            <td class="price"><%= product.getPrice() %>
            </td>
            <td align="center">
                <%--                <input type="hidden" value="<%= entry.getKey().getId() %>">--%>
                <input type="hidden" value="<%= product.getId() %>">
                <input class="count" required type="number" min="1"     <%-- value=""--%> onchange="recalculate(this)">
            </td>
            <%--            <td class="sum"><%= entry.getKey().getPrice() %>--%>
            <td class="sum"><%--<%= product.getPrice() %>--%>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <th align="left" colspan="6">Total</th>
            <th class="totalSum"></th>
        </tr>
    </table>
    <br>
    <form method="post" action="createorder" autocomplete="off">
        <textarea name="comment" placeholder="Input comment about oreder..."></textarea><br>
        <button type="submit">Create order</button>
    </form>
    <div>
        <button class="button" onclick="window.location.href = 'homepage'">To homepage</button>
    </div>
</div>
<jsp:include page="Footer.jsp"/>
<script>

    function buttonDel(el) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'removefromcart');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
        xhr.send('id=' + el.previousElementSibling.value);
        xhr.onload = () => {
            el.parentElement.parentElement.remove();
            calcCart();
            startCalculate();
        }
    }

    startCalculate();

    function startCalculate() {
        let countElements = document.querySelectorAll('.count');
        let priceElements = document.querySelectorAll('.price');
        let sumElements = document.querySelectorAll('.sum');
        if (countElements.length === 0) {
            window.location.href = 'HomePage.jsp';
        }
        for (let element of countElements) {
            element.value = getCount(+element.previousElementSibling.value);
        }
        for (let i = 0; i < countElements.length; i++) {
            sumElements[i].innerHTML = +priceElements[i].innerText * +countElements[i].value;
        }
        totalSum(sumElements);
    }

    function recalculate(el) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'addtocart');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
        xhr.send('id=' + el.previousElementSibling.value + '&count=' + el.value);
        xhr.onload = () => {
            let count = +el.value;
            let price = +el.parentElement.previousElementSibling.innerHTML;
            el.parentElement.nextElementSibling.innerHTML = (price * count).toString();
            let elementsSum = document.querySelectorAll('.sum');
            totalSum(elementsSum);
        }
    }

    function totalSum(allSum) {
        let sum = 0;
        for (let elSum of allSum) {
            sum += +elSum.innerHTML;
        }
        let totalSum = document.querySelector('.totalSum');
        totalSum.innerText = sum;
    }

    //not edit, work
    function getCount(id) {
        let pos = getCookie("cart").lastIndexOf("{");
        let cartItems = JSON.parse(getCookie("cart"));
        // console.log(pos);
        // console.log(cartItems);
        if (pos === 1) {
            return cartItems[0].count;
        } else {
            cartItems = cartItems.find(item => item.id === id);
            return cartItems.count;
        }
    }
</script>
</body>
</html>