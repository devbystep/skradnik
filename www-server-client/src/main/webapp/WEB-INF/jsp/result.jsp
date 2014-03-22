<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="by.minsler.skarnik.beans.Key" %>
<%@page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>«${param.text}» по-белорусски | Skrarnik — русско-белорусский словарь</title>
    <meta charset="utf-8">
    <meta name="description"
          content="Перевод слова «${param.text}» с русского на белорусский язык. Перевести со Скарником легко и быстро!"/>
    <meta name="keywords" content="${param.text}"/>
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/javascript.js"></script>
</head>
<body onload="init();">
<jsp:include page="/WEB-INF/template/header.jsp"/>
<div id="container">



    <div id="content">

    <jsp:include page="/WEB-INF/template/search_input.jsp"/>

        <div id="result">
            <%
                List<String> words = (List<String>) request.getAttribute("list");
                if (words != null) {
                    for (String word : words) {
                        String text = word;
            %>
            <div class="strictlink">
                <a href="translate?text=<%=text%>&strict=yes"><%=text%>
                </a>
            </div>
            <%
                }
            } else if ((String) request.getAttribute("keyText") != null) {

            %>

            <div class="article">
                <div class="key">
                    <%= (String) request.getAttribute("keyText")%>
                </div>
                <div class="def">
                    <%= (String) request.getAttribute("defText")%>
                </div>
            </div>


            <%
            } else {
            %>

            <div class="emptyresult">
                ничего не найдено по тексту ${param.text}
            </div>

            <%
                }
            %>
        </div>

    </div>

</div>
<jsp:include page="/WEB-INF/template/footer.jsp"/>
</body>
</html>