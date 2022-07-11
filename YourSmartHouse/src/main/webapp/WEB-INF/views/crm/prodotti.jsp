<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Gestione Prodotti - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/crm,table"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">

        <button class="button" onclick="create()">Crea nuovo prodotto</button>

        <table class="table">
            <thead>
                <th>Nome</th>
                <th>Categoria</th>
                <th>Prezzo</th>
                <th>Foto</th>
                <th>Disponibilità</th>
            </thead>
            <tbody>
                <c:forEach items="${prodotti}" var="prodotto">
                    <tr>
                        <td data-head="Nome"><a href="${pageContext.request.contextPath}/crm/products/show?id=${prodotto.productId}">${prodotto.nome}</a></td>
                        <td data-head="Categoria"><a href="${pageContext.request.contextPath}/crm/categories/show?id=${prodotto.categoryId}">${prodotto.getNomeCategoria()}</a></td>
                        <td data-head="Prezzo">${prodotto.prezzo}€</td>
                        <td data-head="Foto"><a href="${pageContext.request.contextPath}/photos/${prodotto.foto}" target="_blank">${prodotto.foto}</a> </td>
                        <td data-head="Disponibilità">${prodotto.disponibilita}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <ul class="paginator">
            <c:choose>
                <c:when test="${not empty catId}">
                    <c:set var="plus" value="&categoryId=${catId}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="plus" value=""/>
                </c:otherwise>
            </c:choose>
            <c:forEach var="page" begin="1" end="${pages}">
                <li>
                    <a href="${pageContext.request.contextPath}/crm/products/showAll?page=${page}${plus}">${page}</a>
                </li>
            </c:forEach>
        </ul>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

<script>
    function create() {
        window.location.href = path + "/crm/products/create";
    }
</script>

</body>
</html>
