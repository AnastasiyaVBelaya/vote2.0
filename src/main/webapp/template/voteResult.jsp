<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Результат голосования</title>
</head>
<body>
    <h1>Ваш голос принят!</h1>
    <p>Исполнитель: ${artist}</p>
    <p>Жанры: ${genres}</p>
    <p>О вас: ${about}</p>
    <p><a href="${pageContext.request.contextPath}/browser/results">Посмотреть результаты голосования</a></p>
</body>
</html>