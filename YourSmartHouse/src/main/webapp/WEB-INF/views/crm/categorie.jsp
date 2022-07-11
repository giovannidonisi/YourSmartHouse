<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Gestione Categorie - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/crm,table"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">

        <button class="button" onclick="create()">Crea nuova categoria</button>

        <table class="table">
            <thead>
            <th>Nome</th>
            <th>Foto</th>
            </thead>
            <tbody>
            <c:forEach items="${categorie}" var="categoria">
                <tr>
                    <td data-head="Nome"><a href="${pageContext.request.contextPath}/crm/categories/show?id=${categoria.categoryId}">${categoria.nome}</a></td>
                    <td data-head="Foto"><a href="${pageContext.request.contextPath}/photos/${categoria.foto}" target="_blank">${categoria.foto}</a> </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="paginator">
            <c:forEach var="page" begin="1" end="${pages}">
                <li>
                    <a href="${pageContext.request.contextPath}/crm/categories/showAll?page=${page}">${page}</a>
                </li>
            </c:forEach>
        </ul>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

<script>
    function create() {
        window.location.href = path + "/crm/categories/create";
    }
</script>

</body>
</html>
