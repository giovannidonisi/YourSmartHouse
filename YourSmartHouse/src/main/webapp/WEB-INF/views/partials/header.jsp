<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:choose>
    <c:when test="${not empty utenteSession}">
        <c:set var="account" scope="session" value="accounts/profile"/>
        <c:set var="acc" scope="session" value='<img class="icon" src="${context}/icons/account.svg"> Account'/>
        <c:set var="logout" scope="session" value='<li><a href="${context}/accounts/logout"><img class="icon" src="${context}/icons/logout.svg"> Logout</a></li>'/>
    </c:when>
    <c:otherwise>
        <c:set var="account" scope="session" value="accounts/login"/>
        <c:set var="acc" scope="session" value='<img class="icon" src="${context}/icons/login.svg"> Login</a>'/>
        <c:set var="logout" scope="session" value=""/>
    </c:otherwise>
</c:choose>
<nav>
    <ul>
        <li class="logo"><a href="${context}/"><img id="hlogo" src="${context}/icons/logo.png"
        alt="YourSmartHouse" onmouseover="hover(this);" onmouseout="unhover(this);"></a></li>
        <li class="btn"><img class="icon" id="menu" src="${context}/icons/menu.svg"></li>
        <li class="search-icon">
            <input type="search" id="search" placeholder="Cerca prodotti">
            <a class="s-icon" id="s-icon" href="#"><img class="icon" id="svg-search" src="${context}/icons/search.svg"></a>
            <div id="s-res">

            </div>
        </li>
        <div class="items">
            <li><a href="${context}/"><img class="icon" src="${context}/icons/home.svg"> Home</a></li>
            <li><a href="${context}/products/"><img class="icon" src="${context}/icons/bulb.svg"> Shop</a></li>
            <li><a href="${context}/cart/"><img class="icon" src="${context}/icons/cart.svg"> Carrello</a></li>
            <li><a href="${context}/${account}">${acc}</a></li>
            ${logout}
        </div>
    </ul>
</nav>

<script>
    var path = "${context}";
    $(document).ready( // Richiesta AJAX per i risultati di ricerca
        $('#search').keyup(function(e) {
            let sTxt = $(this).val();
            if (e.keyCode === 13) { //Se viene premuto il tasto "Invio"
                if(sTxt != '')
                    $('#s-icon img').trigger('click');
            }
            if(sTxt != ''){ // Se la barra di ricerca non è vuota
                $.ajax({
                    url: '/YourSmartHouse/products/search/api',
                    method: 'GET',
                    data: {query: sTxt},
                    success: function(data) {
                        $('#s-res').html('');
                        $('#s-res').show();
                        for(let index in data.products) {
                            $('#s-res').append('<li><a href="${context}/products/show?id='+data.products[index].id+'">'+data.products[index].nome+'</a></li>');
                        }
                    },
                    error: function(){
                        $('#s-res').append("Nessun prodotto trovato")
                    }
                })
            }
            else {
                $('#s-res').hide();
            }
        }),

        $('#s-icon').click(function (){
            let sTxt = $('#search').val();
            $(this).attr('href','${context}/products/search?query='+sTxt)
        })
    )
</script>
