<%--
  Created by IntelliJ IDEA.
  User: raulp
  Date: 26/11/2020
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BOOKING</title>
</head>
<body>
<h1>BOOKING LIST</h1>
<form action="${pageContext.request.contextPath}/BookingServlet" METHOD="get">
    <input type="submit" name="submit">
</form>
</body>
</html>
