<%--
  Created by IntelliJ IDEA.
  User: rohanbasu
  Date: 2020-03-18
  Time: 8:08 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <h1>Welcome</h1>
    <hr>
    <p>
        User: <security:authentication property="principal.username" />
        <br><br>
        Role(s): <security:authentication property="authorities" />
    </p>
    <p>
        <security:authorize access="hasRole('MANAGER')">
            <a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">
            <a href="${pageContext.request.contextPath}/systems">Systems Page</a>
        </security:authorize>
    </p>

    <hr>
    <form:form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout" />
    </form:form>

</body>
</html>
