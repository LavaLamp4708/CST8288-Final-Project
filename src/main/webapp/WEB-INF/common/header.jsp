<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% System.out.println("DEBUG: header.jsp started"); %>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="navbar">
        <c:if test="${not empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <c:if test="${sessionScope.user.userType == 'PROFESSIONAL'}">
                <a href="${pageContext.request.contextPath}/course/search">Search Courses</a>
                <a href="${pageContext.request.contextPath}/application/my-applications">My Applications</a>
            </c:if>
            <c:if test="${sessionScope.user.userType == 'INSTITUTION'}">
                <a href="${pageContext.request.contextPath}/course/manage">Manage Courses</a>
                <a href="${pageContext.request.contextPath}/application/manage">Manage Applications</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/notification">Notifications</a>
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </c:if>
    </div>
    <div class="container">
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>
    </div>
