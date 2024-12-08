<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Courses</title>
</head>
<body>
    <h1>Search Courses</h1>

    <!-- Search Form -->
    <form action="SearchServlet" method="get">
        <label for="term">Term:</label>
        <input type="text" id="term" name="term"><br>

        <label for="schedule">Schedule:</label>
        <input type="text" id="schedule" name="schedule"><br>

        <label for="deliveryMethod">Delivery Method:</label>
        <input type="text" id="deliveryMethod" name="deliveryMethod"><br>

        <button type="submit">Search</button>
    </form>

    <!-- Search Results -->
    <h2>Search Results</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Code</th>
                <th>Term</th>
                <th>Schedule</th>
                <th>Delivery Method</th>
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
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
