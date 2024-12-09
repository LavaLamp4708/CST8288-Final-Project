<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Logout" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="logout-container">
        <h2>Logout</h2>
        <p>Are you sure you want to logout?</p>
        
        <form action="${pageContext.request.contextPath}/auth/logout" method="POST" class="form-group">
            <button type="submit" class="btn btn-primary">Confirm Logout</button>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 