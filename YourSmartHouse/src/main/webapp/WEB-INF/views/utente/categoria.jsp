<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Categoria - YourSmartHouse"/>
        <jsp:param name="styles" value="home"/>
    </jsp:include>
    <style>
        .products-index h1 {
            border-top: none; !important;
        }
    </style>
</head>
<body>
<div class="page-container">
    <%@include file="../partials/header.jsp"%>

    <div class="content-wrap">
        <div class="products-index">
            <h1>${cat.nome}</h1>
            <div class="prods">
                <c:forEach items="${prodotti}" var="prod">
                    <div class="product">
                        <a href="${context}/products/show?id=${prod.productId}">
                            <img src="${context}/photos/${prod.foto}">
                            <h3>${prod.nome}</h3>
                            <p>${prod.prezzo}€</p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <ul class="paginator">
            <c:forEach var="page" begin="1" end="${pages}">
                <li>
                    <a href="${pageContext.request.contextPath}/categories/show?id=${cat.categoryId}&page=${page}">${page}</a>
                </li>
            </c:forEach>
        </ul>

    </div>

    <%@include file="../partials/footer.jsp"%>
</div>
</body>
</html>
