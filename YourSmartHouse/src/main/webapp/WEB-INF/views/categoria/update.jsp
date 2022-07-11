<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Modifica Categoria - YourSmartHouse"/>
        <jsp:param name="styles" value="form"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">
        <div class="create box">
            <h2>Modifica Categoria</h2>
            <form id="ccf" action="${pageContext.request.contextPath}/crm/categories/update" method="post" accept-charset="utf-8" enctype="multipart/form-data">
                <c:if test="${not empty alert}">
                    <div class="${alert.type}">
                        <c:forEach var="mes" items="${alert.messages}">
                            <h2>- ${mes}</h2>
                        </c:forEach>
                    </div>
                </c:if>
                <input type="hidden" id="id" name="id" value="${cat.categoryId}">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="${cat.nome}">
                <label for="foto">Foto:</label>
                <input type="file" accept=".jpg" id="foto" name="foto">
                <input type="submit" class="subm" value="Aggiorna">
            </form>
        </div>
    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

</body>
</html>