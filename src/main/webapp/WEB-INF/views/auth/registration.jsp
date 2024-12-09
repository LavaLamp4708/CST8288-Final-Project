<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <h2>User Registration</h2>
    <form action="${pageContext.request.contextPath}/auth/register" method="post" class="form-group">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="userType">User Type:</label>
            <select id="userType" name="userType" required onchange="toggleFields()">
                <option value="">Select User Type</option>
                <option value="Professional">Academic Professional</option>
                <option value="Institution">Academic Institution</option>
            </select>
        </div>

        <div id="professionalFields" style="display: none;" class="conditional-fields">
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName">
            </div>

            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName">
            </div>

            <div class="form-group">
                <label for="currentInstitution">Current Institution:</label>
                <select id="currentInstitution" name="currentInstitution">
                    <c:forEach items="${institutions}" var="inst">
                        <option value="${inst.institutionName}">${inst.institutionName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="academicPosition">Academic Position:</label>
                <input type="text" id="academicPosition" name="academicPosition">
            </div>
        </div>

        <div id="institutionFields" style="display: none;" class="conditional-fields">
            <div class="form-group">
                <label for="institutionName">Institution Name:</label>
                <input type="text" id="institutionName" name="institutionName">
            </div>

            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>
            </div>

            <div class="form-group">
                <label for="contactEmail">Contact Email:</label>
                <input type="email" id="contactEmail" name="contactEmail" required>
            </div>
        </div>

        <button type="submit" class="submit-btn">Register</button>
    </form>
</div>

<script>
function toggleFields() {
    const userType = document.getElementById('userType').value;
    document.getElementById('professionalFields').style.display = 
        userType === 'Professional' ? 'block' : 'none';
    document.getElementById('institutionFields').style.display = 
        userType === 'Institution' ? 'block' : 'none';
}
</script>

<%@ include file="../../common/footer.jsp" %>
