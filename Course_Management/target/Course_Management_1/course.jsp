<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Course Management</title>
</head>
<body>
    <h1>Course Management</h1>

    <!-- Display Success or Error Messages -->
    <c:if test="${param.success != null}">
        <p style="color: green;">${param.success}</p>
    </c:if>
    <c:if test="${param.error != null}">
        <p style="color: red;">${param.error}</p>
    </c:if>

    <!-- Form to Create or Update Course -->
    <form action="CourseServlet" method="post">
        <input type="hidden" name="action" value="create">
        <label for="courseTitle">Course Title:</label>
        <input type="text" id="courseTitle" name="courseTitle" required><br>

        <label for="courseCode">Course Code:</label>
        <input type="text" id="courseCode" name="courseCode" required><br>

        <label for="term">Term:</label>
        <input type="text" id="term" name="term" required><br>

        <label for="schedule">Schedule:</label>
        <input type="text" id="schedule" name="schedule" required><br>

        <label for="deliveryMethod">Delivery Method:</label>
        <input type="text" id="deliveryMethod" name="deliveryMethod" required><br>

        <label for="outline">Course Outline:</label>
        <textarea id="outline" name="outline" required></textarea><br>

        <label for="preferredQualifications">Preferred Qualifications:</label>
        <textarea id="preferredQualifications" name="preferredQualifications"></textarea><br>

        <label for="compensation">Compensation:</label>
        <input type="number" id="compensation" name="compensation" step="0.01" required><br>

        <button type="submit">Create Course</button>
    </form>

    <!-- List of Courses -->
    <h2>Existing Courses</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Code</th>
                <th>Term</th>
                <th>Schedule</th>
                <th>Delivery Method</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="course" items="${courses}">
                <tr>
                    <td>${course.courseId}</td>
                    <td>${course.courseTitle}</td>
                    <td>${course.courseCode}</td>
                    <td>${course.term}</td>
                    <td>${course.schedule}</td>
                    <td>${course.deliveryMethod}</td>
                    <td>
                        <form action="CourseServlet" method="post" style="display: inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="courseId" value="${course.courseId}">
                            <button type="submit">Delete</button>
                        </form>
                            <a href="search.jsp"></a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
