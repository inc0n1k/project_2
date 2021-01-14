<%@ page import="entity.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<%
		String for_ = request.getParameter("for");
		String titl = "";
		switch (for_) {
			case "addo":
				titl = "Add option";
				break;
			case "addp":
				titl = "Add product";
				break;
			case "buy":
				titl = "Buy products";
				break;
			case "edit":
				titl = "Edit product";
				break;
		}

	%>
	<title><%= titl %>
	</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<link href="css/body.css" rel="stylesheet">
	<style>
        .select_cat {
            width: 150px;
            font-family: "Comic Sans MS", sans-serif;
        }

        .box {
            display: flex;
            flex-direction: column;
            border: black double 3px;
            width: 150px;
            padding: 10px;
			border-radius: 5px;
        }
	</style>
</head>
<body>
<jsp:include page="Header.jsp"/>
<div class="body">
	<div class="box">
		<select class="select_cat" id="1" onchange="onCh(this)">
			<option selected disabled>Select category</option>
			<%
				String act = null;
				String method = null;
				if (for_.equals("addo") || for_.equals("addp")) {
					act = "selectcategory";
					method = "post";
				}
				if (for_.equals("buy") || for_.equals("edit")) {
					act = "allproducts";
					method = "get";
				}
				List<Category> categories = (List<Category>) request.getAttribute("categories");
				for (Category category : categories) {
			%>
			<option value="<%= category.getId() %>">
				<%= category.getCategory() %>
			</option>
			<%
				}
			%>
		</select>

		<form method="<%= method %>" action="<%= act %>" autocomplete="off">
			<input name="select_category" type="hidden">
			<input name="for" type="hidden" value="<%= for_ %>">
			<button class="select_cat" disabled type="submit">Next</button>
		</form>
		<button class="select_cat" onclick="window.location.href='homepage'">To home page</button>
	</div>
</div>
<jsp:include page="Footer.jsp"/>
</body>
<script>
    function onCh(el) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
        xhr.send("id=" + el.value);
        xhr.onload = () => {
            let xhr_res = JSON.parse(xhr.response);
            if (xhr_res.length !== 0) {
                let selects = document.querySelectorAll('select');
                for (let elem of selects) {
                    if (elem.id >= xhr_res[0].level) {
                        // elem.parentElement.remove();
                        elem.remove();
                    }
                }
                document.querySelector('button').disabled = true;
                createCategoryList();

                function createCategoryList() {
                    // let div = document.createElement('div');
                    // el.parentElement.after(div);
                    // el.after(div);

                    let select = document.createElement('select');
                    select.setAttribute("class", "select_cat");
                    select.id = xhr_res[0].level;
                    select.onchange = () => {
                        onCh(select);
                    };
                    // div.append(select);
                    el.after(select);

                    let option = document.createElement('option');
                    option.innerText = 'Select category...';
                    option.disabled = true;
                    option.selected = true;
                    select.append(option);

                    for (let el of xhr_res) {
                        let option = document.createElement('option');
                        option.value = el.id;
                        option.innerText = el.category;
                        select.append(option);
                    }
                }
            }
            if (xhr_res.length === 0) {
                document.querySelector('input').value = el.value;
                document.querySelector('button').disabled = false;
            }
        }
    }
</script>
</html>
