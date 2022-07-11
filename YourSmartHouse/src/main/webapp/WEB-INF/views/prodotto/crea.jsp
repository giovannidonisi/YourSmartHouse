<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Crea Prodotto - YourSmartHouse"/>
        <jsp:param name="styles" value="form"/>
    </jsp:include>
</head>
<body>
<div class="page-container">

    <%@include file="../partials/headerCRM.jsp"%>

    <div class="content-wrap">
        <div class="create box">
            <h2>Crea Prodotto</h2>
            <form id="pcf" action="${pageContext.request.contextPath}/crm/products/create" method="post" accept-charset="utf-8" enctype="multipart/form-data">
                <c:if test="${not empty alert}">
                    <div class="${alert.type}">
                            <c:forEach var="mes" items="${alert.messages}">
                                <h2>- ${mes}</h2>
                            </c:forEach>
                    </div>
                </c:if>
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" required>
                <label for="descrizione">Descrizione:</label>
                <textarea form="pcf" id="descrizione" name="descrizione" cols="37" rows="12" required></textarea>
                <label for="prezzo">Prezzo:</label>
                <input type="number" step="0.01" id="prezzo" name="prezzo" required>
                <label for="categoria">Categoria:</label>
                <select id="categoria" name="categoria">
                    <c:forEach var="cat" items="${categorie}">
                        <option value="${cat.categoryId}">${cat.nome}</option>
                    </c:forEach>
                </select>
                <label for="disponibilita">Disponibilità:</label>
                <input type="number" id="disponibilita" name="disponibilita" required>
                <label for="foto">Foto:</label>
                <input type="file" accept=".jpg" id="foto" name="foto" required>
                <input type="submit" class="subm" value="Crea">
            </form>
        </div>
    </div>

    <%@include file="../partials/footer.jsp"%>

</div>

</body>
</html>