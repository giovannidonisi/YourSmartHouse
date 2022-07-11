<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Checkout - YourSmartHouse"/>
        <jsp:param name="styles" value="checkout,table"/>
    </jsp:include>
</head>
<body>

<div class="page-container">
    <%@include file="../partials/header.jsp"%>

    <div class="content-wrap">
        <div class="chkout">
            <div class="user-details">
                <h2>Spedisci a:</h2>
                <h3>Nome: <span>${user.nome}</span></h3>
                <h3>Email: <span>${user.email}</span></h3>
                <h3>Indirizzo: <span>${user.indirizzo}</span></h3>
                <h3>Telefono: <span>${user.telefono}</span></h3>
            </div>

            <div class="order-details">
                <h2>Riepilogo ordine:</h2>
                <h3>Totale: ${cart.getTotale()}€</h3>
                <details>
                    <table class="table">
                        <thead>
                        <th>Nome</th>
                        <th>Quantità</th>
                        <th>Prezzo</th>
                        </thead>
                        <tbody>
                        <c:forEach var="ogg" items="${cart.oggetti}">
                            <tr>
                                <td data-head="Nome"><a href="${context}/products/show?id=${ogg.prodotto.productId}">${ogg.prodotto.nome}</a></td>
                                <td data-head="Quantità">${ogg.quantita}</td>
                                <td data-head="Prezzo">${ogg.totale()}€</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </details>
                <form action="${context}/orders/create" method="post">
                    <button type="submit" class="button">Completa acquisto</button>
                </form>
            </div>
        </div>
    </div>

    <%@include file="../partials/footer.jsp"%>
</div>

</body>
</html>
