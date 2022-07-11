<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Admin Login - YourSmartHouse"/>
        <jsp:param name="styles" value="form"/>
    </jsp:include>
</head>
<body>
<div class="page-container">
<%@include file="../partials/header.jsp"%>
    <div class="content-wrap">
        <div class="login box">
            <form action="${pageContext.request.contextPath}/accounts/secret" method="post">
                <h2>Inserisci Credenziali</h2>
                <c:if test="${not empty alert}">
                    <div class="${alert.type}">
                        <c:forEach var="mes" items="${alert.messages}">
                            <h2>- ${mes}</h2>
                        </c:forEach>
                    </div>
                </c:if>
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" placeholder="example@domain.com" required> <br>
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" placeholder="************" required> <br>
                <input type="submit" class="subm" value="Accedi">
            </form>
        </div>
    </div>
    <%@include file="../partials/footer.jsp"%>
</div>
</body>
</html>
