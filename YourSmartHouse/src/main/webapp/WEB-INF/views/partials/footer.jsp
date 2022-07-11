<c:set var="context" value="${pageContext.request.contextPath}"/>
<button onclick="topFunction()" id="backtotop" title="Torna sù"><img class="icon" src="${context}/icons/top.svg"></button>
<footer>
    <div class="footer-content">
        <table>
            <tr>
                <td><img id="footerlogo" src="${context}/icons/logo.png" onmouseover="hover(this);" onmouseout="unhover(this);"></td>
                <td><h3>&nbsp;YourSmartHouse</h3></td>
            </tr>
        </table>

        <ul class="socials">
            <li><a href="mailto:g.donisi1@studenti.unisa.it" target="_blank"><img class="icon" id="em" src="${context}/icons/email.svg"></a></li>
            <li><a href="https://www.instagram.com/unisalerno/" target="_blank"><img class="icon" id="ig" src="${context}/icons/instagram.svg"></a></li>
            <li><a href="https://twitter.com/unisalerno" target="_blank"><img class="icon" id="tw" src="${context}/icons/twitter.svg"></a></li>
            <li><a href="https://www.youtube.com/channel/UCi3CdtXjmc68QC4uj6xYP4g" target="_blank"><img class="icon" id="yt" src="${context}/icons/youtube.svg"></a></li>
            <li><a href="https://www.facebook.com/unisalerno" target="_blank"><img class="icon" id="fb" src="${context}/icons/facebook.svg"></a></li>
        </ul>
    </div>
    <div class="footer-bottom">
        <p>Leggi la nostra <a href="${context}/privacyPolicy.jsp" target="_blank">Privacy Policy</a></p>
        <p>Copyright &copy;2021 YourSmartHouse - Tutti i diritti riservati.</p>
    </div>
</footer>
<script>
    function hover(element) {
        element.setAttribute('src', '${context}/icons/ylogo.png');
    }
    function unhover(element) {
        element.setAttribute('src', '${context}/icons/logo.png');
    }

    $('.btn #menu').click(function (){ //quando viene cliccato il menu
        let src = $(this).attr('src');
        if(src == '${context}/icons/menu.svg')
            $(this).attr("src","${context}/icons/close.svg");
        else
            $(this).attr("src","${context}/icons/menu.svg");
        $('nav ul div.items').toggleClass("show");
        $(this).toggleClass("show");
        $('.content-wrap').toggleClass("move");
        $('footer').toggleClass("move");
    })

    // bottone per tornare all'inizio della pagina
    const mybutton = document.getElementById("backtotop");
    window.onscroll = function() {scrollFunction()};
    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            mybutton.style.display = "block";
        } else {
            mybutton.style.display = "none";
        }
    }
    function topFunction() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }
</script>