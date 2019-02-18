<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: green
        }
        .exceeded {
            color: red
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<body>
<table border=1>
    <thead>
    <tr>
        <th>ID</th>
        <th>Description</th>
        <th>Date</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"></jsp:useBean>
        <c:set var="color" value="green"/>
        <c:if test="${meal.excess}">
            <c:set var="color" value="red"/>
        </c:if>
        <tr style="color: ${color}">
            <td><c:out value="${meal.id}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td>
                <%=TimeUtil.toString(meal.getDateTime())%>
            </td>
            <td><c:out value="${meal.calories}" /></td>
            <td><a href="meals?action=edit&mealID=<c:out value="${meal.id}"/>">Edit</a></td>
            <td><a href="meals?action=delete&mealID=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="meals?action=add">Add Meal</a>
</body>
</body>
</html>