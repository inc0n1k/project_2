'use strict'

let btn_add = document.getElementById('add_opt');
btn_add.onclick = (e) => {
    e.preventDefault();
    e.stopPropagation();

    let div = document.createElement('div');
    div.setAttribute('class', 'new_opt_div');
    btn_add.before(div);

    let input = document.createElement('input');
    input.required = true;
    input.setAttribute('class', 'new_opt_inp');
    input.setAttribute('type', 'text');
    input.setAttribute('name', 'options');
    input.setAttribute('placeholder', 'Input new option name...');
    div.prepend(input);

    let button = document.createElement('button');
    button.setAttribute('class', 'new_opt_but');
    button.innerText = "qp";
    button.onclick = (e) => {
        e.preventDefault();
        e.stopPropagation();
        DelDiv(button);
    };
    div.append(button);
};

function DelDiv(el) {
    el.parentNode.remove();
}
