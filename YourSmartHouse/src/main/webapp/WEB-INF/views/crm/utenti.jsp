<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Gestione Utenti - YourSmartHouse"/>
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
            <th>Nome</th>
            <th>Email</th>
            </thead>
            <tbody>
            <c:forEach items="${utenti}" var="utente">
                <tr>
                    <td data-head="ID">${utente.userId}</td>
                    <td data-head="Nome"><a href="${pageContext.request.contextPath}/crm/users/show?id=${utente.userId}">${utente.nome}</a></td>
                    <td data-head="Email">${utente.email}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="paginator">
            <c:forEach var="page" begin="1" end="${pages}">
                <li>
                    <a href="${pageContext.request.contextPath}/crm/users/showAll?page=${page}">${page}</a>
                </li>
            </c:forEach>
        </ul>

    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

</body>
</html>
