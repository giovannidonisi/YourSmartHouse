<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Ordini - YourSmartHouse"/>
        <jsp:param name="styles" value="table"/>
    </jsp:include>
    <style>
        details {
            margin: 15px;
            font-weight: bold;
            font-size: 20px;
        }
    </style>
</head>
<body>

<div class="page-container">
    <%@include file="../partials/header.jsp"%>

    <div class="content-wrap">

        <c:choose>
            <c:when test="${ordini.isEmpty()}">
                <h2 style="text-align: center">Nessun ordine effettuato</h2>
            </c:when>
            <c:otherwise>
                <c:forEach items="${ordini}" var="ordine">
                    <details>
                        <summary>N° Ordine: ${ordine.orderId} | Effettuato il: ${ordine.data} | Prezzo: ${ordine.prezzo}€</summary>
                        <table class="table">
                            <thead>
                                <th>Prodotto</th>
                                <th>Quantità</th>
                                <th>Prezzo</th>
                            </thead>
                            <tbody>
                                <c:forEach var="ogg" items="${ordine.carrello.oggetti}">
                                    <tr>
                                        <td data-head="Prodotto">
                                            <a href="${context}/products/show?id=${ogg.prodotto.productId}">${ogg.prodotto.nome}</a>
                                        </td>
                                        <td data-head="Quantità">${ogg.quantita}</td>
                                        <td data-head="Prezzo">${ogg.prodotto.prezzo}€</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </details>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>

    <%@include file="../partials/footer.jsp"%>
</div>

</body>
</html>
