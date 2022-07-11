<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Gestione Categorie - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/show"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">

        <div class="show box">
            <h3>${cat.nome}</h3> <br>
            <h2>ID: ${cat.categoryId}</h2>
            <img src="${pageContext.request.contextPath}/photos/${cat.foto}">
            <div class="show-wrapper">
                <button class="button" onclick="modify()">Modifica</button>
                <button class="button" onclick="show()">Mostra prodotti</button>
            </div>
        </div>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

<script>
    function modify() {
        window.location.href = path + "/crm/categories/update?id=${cat.categoryId}";
    }
    function show() {
        window.location.href = path + "/crm/products/showAll?categoryId=${cat.categoryId}"
    }
</script>

</body>
</html>
