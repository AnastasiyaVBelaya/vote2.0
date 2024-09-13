<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Результаты голосования</title>
</head>
<body>
    <h1>Результаты голосования</h1>

    <h2>Лучший исполнитель</h2>
    <c:forEach var="entry" items="${artistVotes.entrySet()}">
        <p>${entry.key}: ${entry.value} голосов</p>
    </c:forEach>

    <h2>Любимые жанры</h2>
    <c:forEach var="entry" items="${genreVotes.entrySet()}">
        <p>${entry.key}: ${entry.value} голосов</p>
    </c:forEach>

    <h2>О вас</h2>
    <c:forEach var="about" items="${aboutVotes}">
        <p>${about}</p>
    </c:forEach>

    <a href="${pageContext.request.contextPath}/browser/vote">Вернуться на страницу голосования</a><br><br>
</body>
</html>
