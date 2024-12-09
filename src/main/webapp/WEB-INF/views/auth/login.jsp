<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Login" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="form-container">
    <h2>Login</h2>
    <c:if test="${not empty error}">
        <div class="error-message">
            ${error}
        </div>
    </c:if>
    <form action="${pageContext.request.contextPath}/auth/login" method="post" class="form-group">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="submit-btn">Login</button>
        
        <div class="form-footer">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/auth/register">Register here</a></p>
        </div>
    </form>
</div>

<%@ include file="../../common/footer.jsp" %>
