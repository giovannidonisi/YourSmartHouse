
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Il tuo account - YourSmartHouse"/>
    </jsp:include>
    <style>
        h3{
            margin-left: 20px;
        }
        span {
            font-weight: normal;
        }
        #del {
            background: var(--danger) !important;
        }
    </style>
</head>
<body>

<div class="page-container">
    <%@include file="../partials/header.jsp"%>

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
    <div class="content-wrap">

        <h1>Il tuo account</h1><br>

        <button class="button" onclick="orders()">Ordini effettuati</button>

        <h3>Nome: <span>${user.nome}</span></h3>
        <h3>Email: <span>${user.email}</span></h3>
        <h3>Indirizzo: <span>${ind}</span></h3>
        <h3>Telefono: <span>${tel}</span></h3> <br>

        <button class="button" onclick="modify()">Modifica</button>
        <button class="button" onclick="changePwd()">Cambia password</button>
        <form action="${context}/accounts/delete" onsubmit="return conf()" method="post">
            <button class="button" type="submit" id="del">Elimina account</button>
        </form>
    </div>

    <%@include file="../partials/footer.jsp"%>
</div>

<script>
    function modify() {
        window.location.href = path + "/accounts/update";
    }
    function changePwd() {
        window.location.href = path + "/accounts/changePassword";
    }
    function orders() {
        window.location.href = path + "/orders/show";
    }
    function conf() {
        return confirm("Sei sicuro di voler eliminare il tuo account? Questa azione non può essere annullata")
    }
</script>

</body>
</html>
