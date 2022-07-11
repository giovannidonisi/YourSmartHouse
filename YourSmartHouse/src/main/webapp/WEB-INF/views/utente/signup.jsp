<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Registrati - YourSmartHouse"/>
        <jsp:param name="styles" value="form"/>
    </jsp:include>
</head>
<body>
<div class="page-container">
    <%@include file="../partials/header.jsp"%>
    <div class="content-wrap">
        <div class="login box">
            <form action="${pageContext.request.contextPath}/accounts/signup" onsubmit="return checkPwd(this)" method="post">
                <h2>Inserisci Credenziali</h2>
                <c:if test="${not empty alert}">
                    <div class="${alert.type}">
                        <c:forEach var="mes" items="${alert.messages}">
                            <h2>- ${mes}</h2>
                        </c:forEach>
                    </div>
                </c:if>
                <label for="nome">Nome completo:</label>
                <input type="text" id="nome" name="nome" placeholder="Mario Rossi" maxlength="50" required>
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" placeholder="example@domain.com" maxlength="45" required> <br>
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" placeholder="************" minlength="8" maxlength="32" required> <br>
                <label for="passwordConf">Conferma password:</label>
                <input type="password" name="passwordConf" id="passwordConf" placeholder="************" minlength="8" maxlength="32" required> <br>
                <input type="submit" class="subm" value="Registrati">
                <p>Hai già un account? <a href="${pageContext.request.contextPath}/accounts/login">Accedi</a></p>
                <div class="alert danger">
                    <h2>Le password non combaciano!</h2>
                </div>
            </form>
        </div>
    </div>
    <%@include file="../partials/footer.jsp"%>
</div>

<script>
    function checkPwd(){
        let pw1 = document.getElementById("password").value;
        let pw2 = document.getElementById("passwordConf").value;
        if(pw1 !== pw2){
            $(".alert").show(400);
            return false;
        }
        return true;
    }
</script>
</body>
</html>
