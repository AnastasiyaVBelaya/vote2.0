<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Добавить жанр</title>
    <style>
        .form-container {
            width: 300px;
            margin: 0 auto;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
        }
        .form-container input, .form-container textarea {
            width: 100%;
            margin-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .delete-form {
            display: inline;
        }
    </style>
</head>
<body>
    <h2>Добавить новый жанр</h2>
    <div class="form-container">
        <form action="genre" method="post">
            <label for="genreName">Название жанра:</label>
            <input type="text" id="genreName" name="genreName" required>

            <label for="genreDescription">Описание жанра:</label>
            <textarea id="genreDescription" name="genreDescription"></textarea>

            <input type="submit" value="Добавить">
        </form>
    </div>

    <h2>Все жанры</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Описание</th>
                <th>Статус</th>
                <th>Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${genres}">
                <c:if test="${entry.value.status != 'DELETED'}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value.name}</td>
                        <td>${entry.value.description}</td>
                        <td>${entry.value.status}</td>
                        <td>
                            <form action="genre" method="post" class="delete-form">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${entry.key}">
                                <input type="submit" value="Удалить">
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
