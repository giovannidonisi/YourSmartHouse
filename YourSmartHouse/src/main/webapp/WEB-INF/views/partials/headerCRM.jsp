<c:set var="context" value="${pageContext.request.contextPath}"/>
<nav>
    <ul>
        <li class="logo"><a href="${context}/crm/dashboard"><img id="hlogo" src="${context}/icons/logo.png"
        alt="YourSmartHouse" onmouseover="hover(this);" onmouseout="unhover(this);"></a></li>
        <li class="btn"><img class="icon" id="menu" src="${context}/icons/menu.svg"></li>
        <li class="search-icon">
            <input type="search" id="search" placeholder="Cerca prodotti">
            <a class="s-icon" id="s-icon" href="#"><img class="icon" id="svg-search" src="${context}/icons/search.svg"></a>
            <div id="s-res">

            </div>
        </li>
        <div class="items">
            <li><a href="${context}/crm/products/showAll?page=1">Prodotti</a></li>
            <li><a href="${context}/crm/users">Utenti</a></li>
            <li><a href="${context}/crm/orders">Ordini</a></li>
            <li><a href="${context}/crm/categories/showAll?page=1">Categorie</a></li>
            <li><a href="${context}/accounts/logout"> Logout</a></li>
        </div>
    </ul>
</nav>

<script>
    var path = "${context}";
    $(document).ready( //AJAX per i risultati di ricerca
        $('#search').keyup(function(e) {
            let sTxt = $(this).val();
            if (e.keyCode === 13) { //Se viene premuto il tasto "Invio"
                if(sTxt != '')
                    $('#s-icon img').trigger('click');
            }
            if(sTxt != ''){
                $.ajax({
                    url: '/YourSmartHouse/products/search/api',
                    method: 'GET',
                    data: {query: sTxt},
                    success: function(data) {
                        $('#s-res').html('');
                        $('#s-res').show();
                        for(let index in data.products) {
                            $('#s-res').append('<li><a href="${context}/crm/products/show?id='+data.products[index].id+'">'+data.products[index].nome+'</a></li>');
                        }
                    }
                })
            }
            else {
                $('#s-res').hide();
            }
        }),

        $('#s-icon').click(function (){
            let sTxt = $('#search').val();
            $(this).attr('href','${context}/crm/products/search?query='+sTxt)
    })
    )
</script>