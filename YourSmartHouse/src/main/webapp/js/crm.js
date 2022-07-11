const prods = document.getElementsByClassName("grid-y")[0];
prods.addEventListener("click", function (){
    window.location.href = path+"/crm/products/showAll?page=1";
});
const users = document.getElementsByClassName("grid-y")[1];
users.addEventListener("click", function () {
    window.location.href = path+"/crm/users";
});
const orders = document.getElementsByClassName("grid-y")[2];
orders.addEventListener("click", function () {
    window.location.href = path+"/crm/orders";
});
const inc = document.getElementsByClassName("grid-y")[3];
inc.addEventListener("click", function () {
    window.location.href = path+"/crm/orders";
});