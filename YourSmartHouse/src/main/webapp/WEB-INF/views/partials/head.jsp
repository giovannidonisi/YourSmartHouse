<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="height=device-height, width=device-width, initial-scale=1.0, viewport-fit=cover">
<title>${param.title}</title>
<meta name="description" content="E-Commerce di prodotti smart e domotica">
<link rel="icon" type="image/png" href="${context}/icons/favicon.ico"/>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone-no">
<meta name="apple-mobile-web-app-title" content="YourSmartHouse">
<meta name="apple-mobile-web-app-status-bar-style" content="default">
<link rel="apple-touch-icon" href="${context}/icons/logo.png">
<link rel="apple-touch-startup-image" href="${context}/icons/logo.png">
<meta name="theme-color" content="#0b6ab7">
<link rel="stylesheet" href="${context}/css/normalize.css">
<link rel="stylesheet" href="${context}/css/library.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="${context}/css/header.css">
<link rel="stylesheet" href="${context}/css/footer.css">
<c:if test="${not empty param.styles}">
    <c:forTokens items="${param.styles}" delims="," var="style">
        <link rel="stylesheet" href="${context}/css/${style}.css">
    </c:forTokens>
</c:if>
<c:if test="${not empty param.scripts}">
    <c:forTokens items="${param.scripts}" delims="," var="script">
        <script src="${context}/js/${script}.js" defer></script>
    </c:forTokens>
</c:if>