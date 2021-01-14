<%@ page import="entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<link href="css/body.css" rel="stylesheet">
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
		<a href="userorder?ord=<%= order.getId() %>"> Order № <%= String.format("%08d", order.getId()) %>
		</a>
	</div>
	<%
		}
	%>
	<div>
		<button onclick="window.location.href='homepage'">Back</button>
	</div>
	<%
	} else if (request.getAttribute("order") != null) {
		Order order = (Order) request.getAttribute("order");
	%>
	<div>
		<%= "Order № " + String.format("%08d", order.getId()) %><br>
		<%= "Date: " + new SimpleDateFormat("dd/MM/yyy").format(order.getOrder_date()) %><br>
		<%= "Time: " + new SimpleDateFormat("HH:mm").format(order.getOrder_date()) %><br>
		<%= "Comment: " + order.getComment() %><br>
		<%= "User: " + order.getUser().getName() %><br>
		<%= order.getActive() ? "Order status: Open" : "Order status: Close" %><br>
		<%
			if (!order.getActive()) {
				out.print(order.getComplited() ? "Complite" : "Cancel");
			}
		%><br>
	</div>
	<div>
		<button onclick="window.location.href='userorder?id=<%= order.getUser().getId() %>'">Back</button>
	</div>
	<%
		}
	%>
</div>
<%--close body--%>
<jsp:include page="Footer.jsp"/>
</body>
</html>
