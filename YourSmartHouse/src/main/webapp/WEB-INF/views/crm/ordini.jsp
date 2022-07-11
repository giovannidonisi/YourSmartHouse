<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Gestione Ordini - YourSmartHouse"/>
        <jsp:param name="styles" value="crm/crm,table"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">

        <table class="table">
            <thead>
            <th>ID</th>
            <th>Prezzo</th>
            <th>Data</th>
            <th>Utente</th>
            </thead>
            <tbody>
            <c:forEach items="${ordini}" var="ordine">
                <tr>
                    <td data-head="ID"><a href="${context}/crm/orders/show?id=${ordine.orderId}">${ordine.orderId}</a></td>
                    <td data-head="Prezzo">${ordine.prezzo}€</td>
                    <td data-head="Data">${ordine.data}</td>
                    <td data-head="Utente"><a href="${context}/crm/users/show?id=${ordine.userId}">${ordine.userId}</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="paginator">
            <c:forEach var="page" begin="1" end="${pages}">
                <li>
                    <a href="${pageContext.request.contextPath}/crm/orders/showAll?page=${page}">${page}</a>
                </li>
            </c:forEach>
        </ul>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

</body>
</html>
