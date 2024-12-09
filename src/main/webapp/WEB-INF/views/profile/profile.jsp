<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="My Profile" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="profile-container">
        <h2>My Profile</h2>
        
        <form action="${pageContext.request.contextPath}/profile/update" method="post" class="form-group">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" readonly>
            </div>

            <c:if test="${user.userType == 'PROFESSIONAL'}">
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
                </div>

                <div class="form-group">
                    <label for="currentInstitution">Current Institution:</label>
                    <input type="text" id="currentInstitution" name="currentInstitution" 
                           value="${user.currentInstitution}" required>
                </div>

                <div class="form-group">
                    <label for="position">Academic Position:</label>
                    <input type="text" id="position" name="position" value="${user.position}" required>
                </div>
            </c:if>

            <c:if test="${user.userType == 'INSTITUTION'}">
                <div class="form-group">
                    <label for="institutionName">Institution Name:</label>
                    <input type="text" id="institutionName" name="institutionName" 
                           value="${user.institutionName}" required>
                </div>
            </c:if>

            <div class="form-group">
                <label for="newPassword">New Password (leave blank to keep current):</label>
                <input type="password" id="newPassword" name="newPassword">
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword">
            </div>

            <button type="submit" class="submit-btn">Update Profile</button>
        </form>
    </div>

    <c:if test="${user.userType == 'PROFESSIONAL'}">
        <div class="profile-section">
            <h3>My Applications</h3>
            <div class="quick-stats">
                <div class="stat-card">
                    <span class="stat-number">${pendingApplications}</span>
                    <span class="stat-label">Pending Applications</span>
                </div>
                <div class="stat-card">
                    <span class="stat-number">${acceptedApplications}</span>
                    <span class="stat-label">Accepted Applications</span>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/application/my-applications" 
               class="btn btn-primary">View All Applications</a>
        </div>
    </c:if>

    <c:if test="${user.userType == 'INSTITUTION'}">
        <div class="profile-section">
            <h3>My Courses</h3>
            <div class="quick-stats">
                <div class="stat-card">
                    <span class="stat-number">${activeCourses}</span>
                    <span class="stat-label">Active Courses</span>
                </div>
                <div class="stat-card">
                    <span class="stat-number">${pendingApplications}</span>
                    <span class="stat-label">Pending Applications</span>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/course" 
               class="btn btn-primary">Manage Courses</a>
        </div>
    </c:if>
</div>

<script>
document.querySelector('form').addEventListener('submit', function(e) {
    const newPass = document.getElementById('newPassword').value;
    const confirmPass = document.getElementById('confirmPassword').value;
    
    if (newPass || confirmPass) {
        if (newPass !== confirmPass) {
            e.preventDefault();
            alert('New passwords do not match!');
        }
    }
});
</script>

<%@ include file="../../common/footer.jsp" %> 