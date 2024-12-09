<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Registration Confirmation" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="confirmation-card">
        <div class="confirmation-icon">
            <i class="fas fa-check-circle"></i>
        </div>
        
        <h2>Registration Successful!</h2>
        <p>Thank you for registering with our platform.</p>
        
        <div class="confirmation-details">
            <p>We've sent a confirmation email to <strong>${user.email}</strong>.</p>
            <p>Please check your email and click the verification link to activate your account.</p>
        </div>

        <div class="next-steps">
            <h3>Next Steps:</h3>
            <ol>
                <li>Check your email for the verification link</li>
                <li>Click the link to verify your email address</li>
                <li>Complete your profile with additional information</li>
                <c:if test="${user.userType == 'PROFESSIONAL'}">
                    <li>Upload your CV and credentials</li>
                    <li>Start searching for teaching opportunities</li>
                </c:if>
                <c:if test="${user.userType == 'INSTITUTION'}">
                    <li>Add your institution details</li>
                    <li>Start posting course opportunities</li>
                </c:if>
            </ol>
        </div>

        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-primary">Proceed to Login</a>
            <a href="${pageContext.request.contextPath}/profile/edit" class="btn btn-secondary">Complete Your Profile</a>
        </div>

        <div class="help-text">
            <p>Didn't receive the email? 
                <a href="${pageContext.request.contextPath}/auth/resend-verification?email=${user.email}">
                    Resend verification email
                </a>
            </p>
            <p>Need help? <a href="${pageContext.request.contextPath}/help">Contact Support</a></p>
        </div>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 