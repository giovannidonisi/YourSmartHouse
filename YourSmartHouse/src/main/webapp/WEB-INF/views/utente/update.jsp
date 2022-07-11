<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Modifica Profilo - YourSmartHouse"/>
        <jsp:param name="styles" value="form"/>
    </jsp:include>
</head>
<body>
<div class="page-container">
    <%@include file="../partials/header.jsp"%>
    <div class="content-wrap">
        <div class="login box">
            <form action="${pageContext.request.contextPath}/accounts/update" method="post">
                <h2>Inserisci Credenziali</h2>
                <c:if test="${not empty alert}">
                    <div class="${alert.type}">
                        <c:forEach var="mes" items="${alert.messages}">
                            <h2>- ${mes}</h2>
                        </c:forEach>
                    </div>
                </c:if>
                <input type="hidden" name="id" id="id" value="${user.userId}">
                <label for="nome">Nome completo:</label>
                <input type="text" id="nome" name="nome" maxlength="50" value="${user.nome}" required>
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" maxlength="45" value="${user.email}" required> <br>
                <label for="indirizzo">Indirizzo:</label>
                <input type="text" name="indirizzo" id="indirizzo" maxlength="100" value="${user.indirizzo}" required> <br>
                <label for="telefono">Telefono:</label>
                <input type="text" name="telefono" id="telefono" minlength="10" maxlength="20" value="${user.telefono}" required> <br>
                <input type="submit" class="subm" value="Modifica">
            </form>
        </div>
    </div>
    <%@include file="../partials/footer.jsp"%>
</div>

</body>
</html>
