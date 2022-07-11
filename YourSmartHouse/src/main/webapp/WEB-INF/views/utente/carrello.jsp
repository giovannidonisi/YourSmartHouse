<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Carrello - YourSmartHouse"/>
        <jsp:param name="styles" value="table"/>
    </jsp:include>
</head>
<body>

<div class="page-container">
    <%@include file="../partials/header.jsp"%>

    <div class="content-wrap">

        <c:choose>
            <c:when test="${utenteCarrello.oggetti.isEmpty()}">
                <h2 style="text-align: center">Nessun oggetto nel carrello</h2>
            </c:when>
            <c:otherwise>
                <h2 style="text-align: center">Carrello</h2>
                <form action="${context}/orders/checkout">
                    <button class="button" type="submit">Procedi al checkout</button>
                </form>
                <h3>Totale: ${utenteCarrello.getTotale()}€</h3>
                <table class="table">
                    <thead>
                        <th>Nome</th>
                        <th>Quantità</th>
                        <th>Prezzo</th>
                        <th></th>
                    </thead>
                    <tbody>
                    <c:forEach var="ogg" items="${utenteCarrello.oggetti}">
                        <tr>
                            <td data-head="Nome"><a href="${context}/products/show?id=${ogg.prodotto.productId}">${ogg.prodotto.nome}</a></td>
                            <td data-head="Quantità">${ogg.quantita}</td>
                            <td data-head="Prezzo">${ogg.totale()}€</td>
                            <td data-head=""><form action="${context}/cart/remove" method="post">
                                <input type="hidden" name="id" id="id" value="${ogg.prodotto.productId}">
                                <button class="button" type="submit">Rimuovi</button>
                            </form></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form action="${context}/cart/empty" method="post">
                    <button class="button" type="submit">Svuota Carrello</button>
                </form>
            </c:otherwise>
        </c:choose>

    </div>

    <%@include file="../partials/footer.jsp"%>
</div>

</body>
</html>
