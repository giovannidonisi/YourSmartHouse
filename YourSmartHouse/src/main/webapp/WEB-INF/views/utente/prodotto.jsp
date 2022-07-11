<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Prodotto - YourSmartHouse"/>
        <jsp:param name="styles" value="prodotto"/>
    </jsp:include>
</head>
<body>
<div class="page-container">
    <%@include file="../partials/header.jsp"%>

    <div class="content-wrap">
        <div class="prod-page">

            <div class="img-container">
                <img src="${context}/photos/${prod.foto}">
            </div>

            <div class="elements">
                <h4>Categoria: <a href="${context}/categories/show?id=${prod.categoryId}">${prod.getNomeCategoria()}</a></h4>
                <h2>${prod.nome}</h2>
                <h3>${prod.prezzo}€</h3>

                <form method="post" action="${context}/cart/add">
                    <input type="hidden" name="id" id="id" value="${prod.productId}">
                    <c:choose>
                        <c:when test="${prod.disponibilita == 0}">
                            <p style="color: var(--danger)">Esaurito</p>
                        </c:when>
                        <c:otherwise>
                            <label for="quantita">Quantità:</label>
                            <select id="quantita" name="quantita">
                                <c:forEach var="quant" begin="1" end="${prod.disponibilita}">
                                    <option value=${quant}>${quant}</option>
                                </c:forEach>
                            </select>
                            <button class="button" type="submit">Aggiungi al carrello</button>
                        </c:otherwise>
                    </c:choose>
                </form>

                <details>
                    <p>${prod.descrizione}</p>
                </details>

            </div>

        </div>
    </div>

    <%@include file="../partials/footer.jsp"%>
</div>
</body>
</html>
