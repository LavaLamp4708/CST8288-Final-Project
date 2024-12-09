<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Dashboard" scope="request"/>

<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="dashboard-header">
        <h2>Welcome, 
            <c:if test="${user.userType == 'PROFESSIONAL'}">
                ${user.firstName} ${user.lastName}
            </c:if>
            <c:if test="${user.userType == 'INSTITUTION'}">
                ${user.institutionName}
            </c:if>
        </h2>
        <c:if test="${unreadNotifications > 0}">
            <a href="${pageContext.request.contextPath}/notification" class="notification-badge">
                ${unreadNotifications} new notifications
            </a>
        </c:if>
    </div>

    <div class="dashboard-grid">
        <c:if test="${user.userType == 'PROFESSIONAL'}">
            <div class="dashboard-card">
                <h3>My Applications</h3>
                <div class="stat-grid">
                    <div class="stat-item">
                        <span class="stat-number">${pendingApplications}</span>
                        <span class="stat-label">Pending</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number">${acceptedApplications}</span>
                        <span class="stat-label">Accepted</span>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/application/my-applications" 
                   class="btn btn-primary">View Applications</a>
            </div>

            <div class="dashboard-card">
                <h3>Course Search</h3>
                <p>Find new teaching opportunities</p>
                <a href="${pageContext.request.contextPath}/course/search" 
                   class="btn btn-primary">Search Courses</a>
            </div>
        </c:if>

        <c:if test="${user.userType == 'INSTITUTION'}">
            <div class="dashboard-card">
                <h3>Course Management</h3>
                <div class="stat-grid">
                    <div class="stat-item">
                        <span class="stat-number">${activeCourses}</span>
                        <span class="stat-label">Active Courses</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number">${pendingApplications}</span>
                        <span class="stat-label">Pending Applications</span>
                    </div>
                </div>
                <div class="button-group">
                    <a href="${pageContext.request.contextPath}/course/manage" 
                       class="btn btn-primary">Manage Courses</a>
                    <a href="${pageContext.request.contextPath}/course/create" 
                       class="btn btn-secondary">Create Course</a>
                </div>
            </div>

            <div class="dashboard-card">
                <h3>Recent Applications</h3>
                <div class="recent-list">
                    <c:forEach items="${recentApplications}" var="app" end="4">
                        <div class="recent-item">
                            <span>${app.professional.firstName} ${app.professional.lastName}</span>
                            <span>applied to</span>
                            <span>${app.course.courseTitle}</span>
                        </div>
                    </c:forEach>
                </div>
                <a href="${pageContext.request.contextPath}/application/manage" 
                   class="btn btn-primary">View All Applications</a>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 