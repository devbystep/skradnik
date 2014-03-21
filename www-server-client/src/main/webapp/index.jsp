<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Русско-белорусский онлайн-словарь | Руска-беларускі анлайн-слоўнік | Skrarnik</title>
    <meta charset="utf-8">
    <meta name="description"
          content="Электронный русско-белорусский словарь Skrarnik."/>
    <meta name="keywords" content="русско-белорусский словарь"/>
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/javascript.js"></script>
</head>
<body onload="init();">
<jsp:include page="/WEB-INF/template/header.jsp"/>
<div id="container">
    <div id="content">
    <jsp:include page="/WEB-INF/template/search_input.jsp"/>
    </div>
</div>
<jsp:include page="/WEB-INF/template/footer.jsp"/>
</body>
</html>