<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="My Applications" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <h2>My Course Applications</h2>
    
    <div class="applications-grid">
        <c:forEach items="${applications}" var="app">
            <div class="application-card">
                <h3>${app.course.courseTitle}</h3>
                <div class="application-info">
                    <p><strong>Institution:</strong> ${app.course.institutionName}</p>
                    <p><strong>Status:</strong> <span class="status-${fn:toLowerCase(app.status)}">${app.status}</span></p>
                    <p><strong>Applied on:</strong> ${app.applicationDate}</p>
                    
                    <c:if test="${not empty app.feedback}">
                        <div class="feedback-section">
                            <h4>Feedback</h4>
                            <p>${app.feedback}</p>
                        </div>
                    </c:if>
                    
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/course/view/${app.course.courseId}" 
                           class="btn btn-secondary">View Course</a>
                           
                        <c:if test="${app.status == 'PENDING'}">
                            <form action="${pageContext.request.contextPath}/application/withdraw" method="POST" style="display: inline;">
                                <input type="hidden" name="applicationId" value="${app.applicationId}">
                                <button type="submit" class="btn btn-danger">Withdraw</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
        
        <c:if test="${empty applications}">
            <div class="empty-state">
                <p>You haven't submitted any applications yet.</p>
                <a href="${pageContext.request.contextPath}/course/search" class="btn btn-primary">Search Courses</a>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 