<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Modifica Password - YourSmartHouse"/>
        <jsp:param name="styles" value="form"/>
    </jsp:include>
</head>
<body>
<div class="page-container">
    <%@include file="../partials/header.jsp"%>
    <div class="content-wrap">
        <div class="login box">
            <form action="${pageContext.request.contextPath}/accounts/changePassword" onsubmit="return checkPwd(this)" method="post">
                <h2>Inserisci Credenziali</h2>
                <c:if test="${not empty alert}">
                    <div class="${alert.type}">
                        <c:forEach var="mes" items="${alert.messages}">
                            <h2>- ${mes}</h2>
                        </c:forEach>
                    </div>
                </c:if>
                <input type="hidden" name="id" id="id" value="${id}">
                <label for="oldPassword">Vecchia password:</label>
                <input type="password" name="oldPassword" id="oldPassword" placeholder="************" minlength="8" maxlength="32" required> <br>
                <label for="newPassword">Nuova password:</label>
                <input type="password" name="newPassword" id="newPassword" placeholder="************" minlength="8" maxlength="32" required> <br>
                <label for="newPasswordConf">Conferma nuova password:</label>
                <input type="password" name="newPasswordConf" id="newPasswordConf" placeholder="************" minlength="8" maxlength="32" required> <br>
                <input type="submit" class="subm" value="Modifica">
                <div class="alert danger">
                    <h2>Le nuove password non combaciano!</h2>
                </div>
            </form>
        </div>
    </div>
    <%@include file="../partials/footer.jsp"%>
</div>

<script>
    function checkPwd(){
        var pw1 = document.getElementById("newPassword").value;
        var pw2 = document.getElementById("newPasswordConf").value;
        if(pw1 !== pw2){
            $(".alert").show(400);
            return false;
        }
        return true;
    }
</script>
</body>
</html>
