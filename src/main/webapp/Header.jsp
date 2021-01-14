<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="css/header.css" rel="stylesheet">
<div class="header">
    <div class="logo">
        <img src="images/1q2w3e.png">
    </div>
    <div class="right_head">
        <a href="cart">
            <img class="basket" src="images/4r5t6y.png">
        </a>
        <a href="cart">
            <div class="calc_prod_in_basket">
                <div id="calc" class="calc">
                </div>
            </div>
        </a>
        <div id="login_info" class="login_info">
            Вы вошли как:
        </div>
    </div>
</div>
<script>

    // ****************

    function getCookie(name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }

    function calcCart() {
        calc.innerText = getCookie("count") === undefined ? 0 : getCookie("count");
    }

    calcCart();

    // ****************

    function authUser() {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'getauthuser');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
        xhr.send();
        xhr.onload = () => {
            if (xhr.response !== "null") {
                let user = JSON.parse(xhr.response);
                let a = document.createElement('a');
                a.href = 'userprofile';
                a.innerText = user.name + '(' + user.login + ')';
                login_info.append(a);
                let a2 = document.createElement('a');
                a2.href = 'exit';
                a2.innerText = 'Exit';
                a.after(a2);
            } else {
                let a = document.createElement('a');
                a.href = 'login';
                a.innerText = 'Log in';
                login_info.append("Guest");
                login_info.append(a);
            }
        }
    }

    authUser();

    // ****************

</script>

