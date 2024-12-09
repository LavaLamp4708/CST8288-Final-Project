<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="My Notifications" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <h2>My Notifications</h2>

    <div class="notification-list">
        <c:forEach items="${notifications}" var="notification">
            <div class="notification-card ${notification.read ? 'read' : 'unread'}">
                <div class="notification-header">
                    <span class="notification-date">${notification.createdAt}</span>
                    <c:if test="${!notification.read}">
                        <span class="unread-badge">New</span>
                    </c:if>
                </div>
                
                <div class="notification-content">
                    <h4>${notification.title}</h4>
                    <p>${notification.message}</p>
                    
                    <c:if test="${not empty notification.actionUrl}">
                        <a href="${pageContext.request.contextPath}${notification.actionUrl}" 
                           class="btn btn-primary">View Details</a>
                    </c:if>
                </div>

                <c:if test="${!notification.read}">
                    <form action="${pageContext.request.contextPath}/notification/mark-read" 
                          method="POST" class="mark-read-form">
                        <input type="hidden" name="notificationId" value="${notification.id}">
                        <button type="submit" class="btn btn-text">Mark as Read</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>

        <c:if test="${empty notifications}">
            <div class="empty-state">
                <p>You have no notifications</p>
            </div>
        </c:if>
    </div>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}" class="btn btn-secondary">&laquo; Previous</a>
        </c:if>
        <c:if test="${hasNextPage}">
            <a href="?page=${currentPage + 1}" class="btn btn-secondary">Next &raquo;</a>
        </c:if>
    </div>

    <c:if test="${not empty unreadCount && unreadCount > 0}">
        <form action="${pageContext.request.contextPath}/notification/mark-all-read" 
              method="POST" class="mark-all-read">
            <button type="submit" class="btn btn-secondary">Mark All as Read</button>
        </form>
    </c:if>
</div>

<%@ include file="../../common/footer.jsp" %> 