<%@ page import="entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.OrderProduct" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>All orders</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<link href="css/body.css" rel="stylesheet">
	<style>
        button {
            padding: 2px 5px;
            font-family: "Comic Sans MS", sans-serif;
            border-radius: 4px;
            margin: 4px;
        }
	</style>
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
	<%
		if (request.getAttribute("orders") != null) {
			List<Order> orders = (List<Order>) request.getAttribute("orders");
			for (Order order : orders) {
	%>
	<div>
		<a href="allorders?id=<%= order.getId() %>">
			Order № <%= String.format("%08d", order.getId())%>
		</a>
	</div>
	<%
		}
	} else if (request.getAttribute("order") != null) {
	%>
	<div>
		<%
			Order order = (Order) request.getAttribute("order");
			out.print("Order № " + String.format("%08d", order.getId()) + "<br>");
			if (order.getUser() == null) {
				out.print("User name: Guest<br>");
			} else {
				out.print("User name: " + order.getUser().getName() + " (" + order.getUser().getLogin() + ")" + "<br>");
			}
			out.print("Order date: " + new SimpleDateFormat("dd.MM.yyyy").format(order.getOrder_date()) + "<br>");
			out.print("Order time: " + new SimpleDateFormat("HH:mm").format(order.getOrder_date()) + "<br>");
			out.print("Comment: " + order.getComment() + "<br><br>");
			for (OrderProduct orderProduct : order.getOrderProducts()) {
				out.print(orderProduct.getProduct().getName() + " : " + orderProduct.getCount() + " шт.");
				out.print("<br>");
			}
		%>
	</div>
	<form method="post" action="closeorder" autocomplete="off">
		<input type="hidden" name="order" value="<%= order.getId() %>">
		<div>
			<input id="active" type="checkbox" onchange="orderClose(this)"> Close order
		</div>
		<div>
			<input checked type="radio" name="complite" value="true" onchange="commText(this)"> Complite
			<input type="radio" name="complite" value="false" onchange="commText(this)"> Don't complite
		</div>
	</form>
	<div>
		<button class="button" onclick="window.location.href='allorders'"> Back</button>
	</div>
	<%
		}
	%>
	<div>
		<button class="button" onclick="window.location.href = 'homepage'">To homepage</button>
	</div>
</div>
<jsp:include page="Footer.jsp"/>
<script>

    function commText(el) {
        if (el.value === 'true') {
            commDiv.remove();
        } else {
            let div = document.createElement('div');
            div.id = 'commDiv';
            let text = document.createElement('textarea');
            text.name = 'comment';
            text.required = true;
            div.append(text);
            el.parentElement.after(div);
        }
    }

    function orderClose(el) {
        if (el.checked) {
            let button = document.createElement('button');
            button.id = 'save';
            button.type = 'submit';
            button.innerText = 'Complite';
            document.querySelector('form').append(button);
        } else {
            save.remove();
        }
    }

</script>
</body>
</html>
