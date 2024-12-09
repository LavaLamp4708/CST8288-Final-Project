<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Forgot Password" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="auth-card">
        <h2>Forgot Password</h2>
        <p>Enter your email address and we'll send you instructions to reset your password.</p>

        <form action="${pageContext.request.contextPath}/auth/forgot-password" method="POST" class="form-group">
            <div class="form-group">
                <label for="email">Email Address:</label>
                <input type="email" id="email" name="email" required 
                       placeholder="Enter your registered email">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Send Reset Link</button>
                <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-link">Back to Login</a>
            </div>
        </form>

        <div class="help-text">
            <p>Remember your password? 
                <a href="${pageContext.request.contextPath}/auth/login">Sign in</a>
            </p>
            <p>Don't have an account? 
                <a href="${pageContext.request.contextPath}/auth/register">Register now</a>
            </p>
        </div>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 