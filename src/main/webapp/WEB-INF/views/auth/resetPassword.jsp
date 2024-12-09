<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Reset Password" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="auth-card">
        <h2>Reset Password</h2>
        <p>Please enter your new password below.</p>

        <form action="${pageContext.request.contextPath}/auth/reset-password" method="POST" 
              class="form-group" onsubmit="return validateForm()">
            <input type="hidden" name="token" value="${param.token}">
            
            <div class="form-group">
                <label for="password">New Password:</label>
                <input type="password" id="password" name="password" required 
                       minlength="8" placeholder="Enter new password">
                <small class="form-text">
                    Password must be at least 8 characters long and include uppercase, lowercase, 
                    number, and special character.
                </small>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required 
                       minlength="8" placeholder="Confirm new password">
            </div>

            <div class="password-strength">
                <div class="strength-meter"></div>
                <p class="strength-text"></p>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Reset Password</button>
                <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-link">Cancel</a>
            </div>
        </form>
    </div>
</div>

<script>
function validateForm() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    if (password !== confirmPassword) {
        alert('Passwords do not match!');
        return false;
    }
    
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordRegex.test(password)) {
        alert('Password must meet all requirements!');
        return false;
    }
    
    return true;
}

// Password strength meter
document.getElementById('password').addEventListener('input', function(e) {
    const password = e.target.value;
    const strengthMeter = document.querySelector('.strength-meter');
    const strengthText = document.querySelector('.strength-text');
    
    let strength = 0;
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[a-z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;
    
    const strengthPercentage = (strength / 5) * 100;
    strengthMeter.style.width = strengthPercentage + '%';
    
    switch(strength) {
        case 1: strengthText.textContent = 'Weak'; break;
        case 2: strengthText.textContent = 'Fair'; break;
        case 3: strengthText.textContent = 'Good'; break;
        case 4: strengthText.textContent = 'Strong'; break;
        case 5: strengthText.textContent = 'Very Strong'; break;
        default: strengthText.textContent = ''; break;
    }
});
</script>

<%@ include file="../../common/footer.jsp" %> 