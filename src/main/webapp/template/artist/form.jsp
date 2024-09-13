<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Добавить исполнителя</title>
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
    <h2>Добавить нового исполнителя</h2>
    <div class="form-container">
        <form action="artist" method="post">
            <label for="artistName">Имя исполнителя:</label>
            <input type="text" id="artistName" name="artistName" required>

            <label for="genre">Жанр:</label>
            <input type="text" id="genre" name="genre" required>

            <label for="biography">Биография:</label>
            <textarea id="biography" name="biography"></textarea>

            <input type="submit" value="Добавить">
        </form>
    </div>

    <h2>Все исполнители</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Жанры</th>
                <th>Биография</th>
                <th>Дата создания</th>
                <th>Дата модификации</th>
                <th>Статус</th>
                <th>Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${artists}">
                <c:if test="${entry.value.status != 'DELETED'}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value.name}</td>
                        <td>
                            <c:if test="${entry.value.genre != null}">
                                <c:out value="${entry.value.genre}"/>
                            </c:if>
                        </td>
                        <td>${entry.value.biography}</td>
                        <td>${entry.value.dateCreated}</td>
                        <td>${entry.value.lastModified}</td>
                        <td>${entry.value.status}</td>
                        <td>
                            <form action="artist" method="post" class="delete-form">
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
