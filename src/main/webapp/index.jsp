
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Academic Exchange Platform</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Welcome to Academic Exchange Platform</h1>
        
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="auth-buttons">
                    <a href="${pageContext.request.contextPath}/auth/login" class="btn">Login</a>
                    <a href="${pageContext.request.contextPath}/auth/register" class="btn">Register</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="dashboard">
                    <h2>Quick Links</h2>
                    <div class="quick-links">
                        <a href="course/search">Search Courses</a>
                        <a href="profile">My Profile</a>
                        <c:if test="${sessionScope.user.userType == 'PROFESSIONAL'}">
                            <a href="application/my-applications">My Applications</a>
                        </c:if>
                        <c:if test="${sessionScope.user.userType == 'INSTITUTION'}">
                            <a href="course/manage">Manage Courses</a>
                        </c:if>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html> 
