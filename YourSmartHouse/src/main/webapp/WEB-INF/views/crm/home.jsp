<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Admin Home - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/crm"/>
        <jsp:param name="scripts" value="crm"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

<%@include file="../partials/headerCRM.jsp"%>

<div class="content-wrap">
    <div class="grid-container">
        <div class="grid-y">
            <h2>Prodotti in magazzino</h2>
            <h4>${nProd}</h4>
        </div> <br>
        <div class="grid-y">
            <h2>Utenti Registrati</h2>
            <h4>${nAcc}</h4>
        </div> <br>
        <div class="grid-y">
            <h2>Ordini Totali</h2>
            <h4>${nOrd}</h4>
        </div> <br>
        <div class="grid-y">
            <h2>Incasso Totale</h2>
            <h4>${tot} €</h4>
        </div> <br>
    </div>
</div>

<%@include file="../partials/footer.jsp"%>

</div>

</body>
</html>
