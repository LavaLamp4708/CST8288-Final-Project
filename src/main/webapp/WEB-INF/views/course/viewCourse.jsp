<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="${course.courseTitle}" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="course-details">
        <h2>${course.courseTitle}</h2>
        <div class="info-grid">
            <p><strong>Course Code:</strong> ${course.courseCode}</p>
            <p><strong>Term:</strong> ${course.term}</p>
            <p><strong>Schedule:</strong> ${course.schedule}</p>
            <p><strong>Delivery Method:</strong> ${course.deliveryMethod}</p>
            <p><strong>Compensation:</strong> $${course.compensation}</p>
            <p><strong>Application Deadline:</strong> ${course.applicationDeadline}</p>
        </div>
        
        <section class="content-section">
            <h3>Course Outline</h3>
            <p>${course.outline}</p>
        </section>
        
        <section class="content-section">
            <h3>Preferred Qualifications</h3>
            <p>${course.preferredQualifications}</p>
        </section>
    </div>

    <c:if test="${userType == 'PROFESSIONAL' && course.status == 'ACTIVE'}">
        <div class="form-container">
            <h3>Apply for this Course</h3>
            <form action="${pageContext.request.contextPath}/application/submit" method="POST" class="form-group">
                <input type="hidden" name="courseId" value="${course.courseId}">
                
                <div class="form-group">
                    <label for="coverLetter">Cover Letter:</label>
                    <textarea id="coverLetter" name="coverLetter" rows="6" required></textarea>
                </div>
                
                <div class="form-group">
                    <label for="additionalDocs">Additional Documents (Optional):</label>
                    <input type="file" id="additionalDocs" name="additionalDocs">
                </div>
                
                <button type="submit" class="submit-btn">Submit Application</button>
            </form>
        </div>
    </c:if>
</div>

<%@ include file="../../common/footer.jsp" %> 