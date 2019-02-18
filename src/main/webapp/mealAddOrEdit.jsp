<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal edit</title>
</head>
<body>
<h3><a href="meals">Meal list</a></h3>
<c:if test="${not empty meal}" >
    Edit Meal
</c:if>
<c:if test="${empty meal}" >
    Add Meal
</c:if>

<form method="post" action="meals">
    <table>
        <c:if test="${not empty meal}">
        <tr>
            <h3>ID</h3>
            <input readonly type="text" name="id" value="<c:out value="${meal.id}"/> ">
        </tr>
        </c:if>

        <tr>
            <h3>Description</h3>
            <input type="text" name="description" required value="<c:out value="${meal.description}"/>">
        </tr>

        <tr>
            <h3>Date</h3>
            <input type="datetime-local" name="dateTime" required value="<c:out value="${meal.dateTime}"/>">
        </tr>

        <tr>
            <h3>Calories</h3>
            <input type="number" name="calories" required value="<c:out value="${meal.calories}" />">
        </tr>

    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
