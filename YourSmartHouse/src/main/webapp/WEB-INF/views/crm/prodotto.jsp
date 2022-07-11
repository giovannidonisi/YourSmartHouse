<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Pagina Prodotto - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/show"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">

        <div class="show box">
            <h3>${prod.nome}</h3><br>
            <h2>ID: ${prod.productId}<br>Unità in magazzino: ${prod.disponibilita}</h2>
            <img src="${pageContext.request.contextPath}/photos/${prod.foto}">
            <p>${prod.descrizione}</p>
            <div class="show-wrapper">
                <button class="button" onclick="modify()">Modifica</button>
                <button class="button" onclick="del()"
                title="Nota: non elimina definitivamente il prodotto, ma imposta la disponibilità a zero">Elimina</button>
            </div>
        </div>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

<script>
    function modify() {
        window.location.href = path + "/crm/products/update?id=${prod.productId}";
    }
    function del() {
        window.location.href = path + "/crm/products/delete?id=${prod.productId}";
    }
</script>

</body>
</html>