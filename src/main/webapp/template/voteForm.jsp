<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Форма для голосования</title>
    <style>
        textarea {
            width: 50%;
            height: 100px;
            margin-top: 10px;
        }
    </style>
    <script>

        window.onload = function() {
            var form = document.getElementById("voteForm");
            if (form) {
                form.reset();
            }
        };


        function validateForm() {
            var checkboxes = document.querySelectorAll('input[name="genre"]:checked');
            if (checkboxes.length < 3) {
                alert("Пожалуйста, выберите не менее трех жанров.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<form action="${pageContext.request.contextPath}/browser/vote" method="post" id="voteForm" onsubmit="return validateForm();">
    <p><b>Лучший исполнитель</b></p>
    <c:forEach items="${artists}" var="item">
        <label>
            <input type="radio" name="artist" value="${item.id}" required> ${item.name}
        </label><br>
    </c:forEach>
    <br>

    <p><b>Ваши любимые жанры</b></p>
    <c:forEach items="${genres}" var="item">
        <label>
            <input type="checkbox" name="genre" value="${item.id}"> ${item.name}
        </label><br>
    </c:forEach>
    <br>

    <p><b>Краткий текст о вас</b></p>
    <textarea name="about" required></textarea>

    <p><input type="submit" value="Подтвердить и отправить"></p><br>

    <p><a href="${pageContext.request.contextPath}/browser/results">Посмотреть текущие результаты голосования</a></p>
</form>
</body>
</html>
