<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Pagina Ordine - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/show"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">

        <div class="show box">
            <h3>ID: ${order.orderId}</h3><br>
            <h2>
                Quantità: ${order.quantita}<br>
                Prezzo: ${order.prezzo}€<br>
                Data: ${order.data}<br>
                Utente: <a href="${context}/crm/users/show?id=${order.userId}">${order.userId}</a><br>
            </h2>
        </div>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

</body>
</html>