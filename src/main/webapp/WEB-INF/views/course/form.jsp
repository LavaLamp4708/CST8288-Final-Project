<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Create Course" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="form-container">
    <h2>Create New Course</h2>
    
    <form action="${pageContext.request.contextPath}/course/create" method="post" class="form-group">
        <div class="form-group">
            <label for="courseTitle">Course Title:</label>
            <input type="text" id="courseTitle" name="courseTitle" required>
        </div>

        <div class="form-group">
            <label for="courseCode">Course Code:</label>
            <input type="text" id="courseCode" name="courseCode" required>
        </div>

        <div class="form-group">
            <label for="term">Term:</label>
            <input type="text" id="term" name="term" required>
        </div>

        <div class="form-group">
            <label for="schedule">Schedule:</label>
            <select id="schedule" name="schedule" required>
                <option value="MORNING">Morning</option>
                <option value="AFTERNOON">Afternoon</option>
                <option value="EVENING">Evening</option>
            </select>
        </div>

        <div class="form-group">
            <label for="deliveryMethod">Delivery Method:</label>
            <select id="deliveryMethod" name="deliveryMethod" required>
                <option value="IN_PERSON">In Person</option>
                <option value="REMOTE">Remote</option>
                <option value="HYBRID">Hybrid</option>
            </select>
        </div>

        <div class="form-group">
            <label for="outline">Course Outline:</label>
            <textarea id="outline" name="outline" rows="6" required></textarea>
        </div>

        <div class="form-group">
            <label for="preferredQualifications">Preferred Qualifications:</label>
            <textarea id="preferredQualifications" name="preferredQualifications" rows="4"></textarea>
        </div>

        <div class="form-group">
            <label for="compensation">Compensation:</label>
            <input type="number" id="compensation" name="compensation" step="0.01" required>
        </div>

        <div class="form-group">
            <label for="applicationDeadline">Application Deadline:</label>
            <input type="date" id="applicationDeadline" name="applicationDeadline" required>
        </div>

        <button type="submit" class="submit-btn">Create Course</button>
    </form>
</div>

<%@ include file="../../common/footer.jsp" %> 