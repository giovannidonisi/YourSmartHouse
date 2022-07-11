<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Pagina Utente - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/show"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">
        <c:choose>
            <c:when test="${not empty user.telefono}">
                <c:set var="tel" value="${user.telefono}"/>
            </c:when>
            <c:otherwise>
                <c:set var="tel" value="Non presente"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${not empty user.indirizzo}">
                <c:set var="ind" value="${user.indirizzo}"/>
            </c:when>
            <c:otherwise>
                <c:set var="ind" value="Non presente"/>
            </c:otherwise>
        </c:choose>
        <div class="show box">
            <h3>${user.nome}</h3><br>
            <h2>
                ID: ${user.userId}<br>
                Email: ${user.email}<br>
                Indirizzo: ${ind}<br>
                Telefono: ${tel}<br>
            </h2>
            <div class="show-wrapper">
                <button class="button" onclick="orders()">Mostra Ordini</button>
            </div>
        </div>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

<script>
    function orders() {
        window.location.href = path + "/crm/orders/user?id=${user.userId}";
    }
</script>

</body>
</html>