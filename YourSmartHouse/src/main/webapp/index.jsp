<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Home - YourSmartHouse"/>
        <jsp:param name="styles" value="home"/>
    </jsp:include>
</head>
<body>
<div class="page-container">
<%@include file="WEB-INF/views/partials/header.jsp"%>

<div class="content-wrap">
    <div class="categories-index">

    </div>

    <div class="products-index">

    </div>
</div>

<%@include file="WEB-INF/views/partials/footer.jsp"%>
</div>

<script>
    $(document).ready(
        $('.categories-index').append('<h1>Categorie</h1> <div class="categs"></div>'),
        $.ajax({ // Richiesta AJAX per riempire le categorie nella pagina iniziale
            url: '/YourSmartHouse/categories/api',
            method: 'GET',
            success: function(data) {
                for(let index in data.categories) {
                    $('.categs').append( // Costruisce i div con le informazioni recuperate
                        '<div class="category"> <a href="${context}/categories/show?id='+
                         data.categories[index].id + '">' +
                        '<img src="${context}/photos/' + data.categories[index].foto + '">'
                        + '<h3>' + data.categories[index].nome + '<h3></a> </div>'
                    )
                }
            }
        }),

        $('.products-index').append('<h1>In primo piano</h1> <div class="prods"></div>'),
        $.ajax({ // Richiesta AJAX per riempire i prodotti in primo piano nella pagina iniziale
            url: '/YourSmartHouse/products/featured/api',
            method: 'GET',
            success: function(data) {
                for(let index in data.prods) {
                    $('.prods').append( // Costruisce i div con le informazioni recuperate
                        '<div class="product"> <a href="${context}/products/show?id='
                        + data.prods[index].id + '">' + '<img src="${context}/photos/' +
                        data.prods[index].foto + '">' + '<h3>' + data.prods[index].nome +
                        ' </h3></a> <p>' + data.prods[index].prezzo + '€</p> </div>'
                    )
                }
            }
        })
    )
</script>

</body>
</html>
