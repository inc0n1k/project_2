'use strict'

function addProductToCart(element) {
    let buyElement = element.getAttribute('value');
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'addtocart');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    xhr.send('id=' + buyElement);
    xhr.onload = () => {
        calcCart()
    }
}