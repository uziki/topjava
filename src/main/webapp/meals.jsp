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
        <th>Description</th>
        <th>Date</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"></jsp:useBean>
        <tr Class = ${meal.excess ? 'exceeded' : 'normal'}>
            <td><c:out value="${meal.description}" /></td>
            <td>
                <%=TimeUtil.toString(meal.getDateTime())%>
            </td>
            <td><c:out value="${meal.calories}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</body>
</html>