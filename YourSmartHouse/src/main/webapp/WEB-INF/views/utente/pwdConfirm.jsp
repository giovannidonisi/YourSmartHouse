<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Modifica Password - YourSmartHouse</title>
    <c:set var="context" value="${pageContext.request.contextPath}"/>
    <link rel="icon" href="${context}/icons/favicon.ico"/>
    <link rel="stylesheet" href="${context}/css/normalize.css">
    <link rel="stylesheet" href="${context}/css/library.css">
    <link rel="stylesheet" href="${context}/css/errorPage.css">
</head>
<body>
<div id="main" onclick="window.location.replace('/YourSmartHouse/accounts/')">
    <div class="err">
        <h1>
            Password modificata correttamente! <br>
            <span>Clicca per tornare al profilo</span>
        </h1>
    </div>
</div>
</body>
</html>
