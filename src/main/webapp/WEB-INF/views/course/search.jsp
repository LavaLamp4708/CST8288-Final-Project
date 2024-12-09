<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Search Courses" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="search-section">
        <h2>Search Courses</h2>
        <form action="${pageContext.request.contextPath}/course/search" method="GET" class="form-group">
            <div class="search-grid">
                <div class="form-group">
                    <label for="keyword">Keyword:</label>
                    <input type="text" id="keyword" name="keyword" value="${param.keyword}">
                </div>

                <div class="form-group">
                    <label for="term">Term:</label>
                    <select id="term" name="term">
                        <option value="">Any Term</option>
                        <c:forEach items="${terms}" var="term">
                            <option value="${term}" ${param.term == term ? 'selected' : ''}>${term}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="schedule">Schedule:</label>
                    <select id="schedule" name="schedule">
                        <option value="">Any Schedule</option>
                        <option value="MORNING" ${param.schedule == 'MORNING' ? 'selected' : ''}>Morning</option>
                        <option value="AFTERNOON" ${param.schedule == 'AFTERNOON' ? 'selected' : ''}>Afternoon</option>
                        <option value="EVENING" ${param.schedule == 'EVENING' ? 'selected' : ''}>Evening</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="deliveryMethod">Delivery Method:</label>
                    <select id="deliveryMethod" name="deliveryMethod">
                        <option value="">Any Method</option>
                        <option value="IN_PERSON" ${param.deliveryMethod == 'IN_PERSON' ? 'selected' : ''}>In Person</option>
                        <option value="REMOTE" ${param.deliveryMethod == 'REMOTE' ? 'selected' : ''}>Remote</option>
                        <option value="HYBRID" ${param.deliveryMethod == 'HYBRID' ? 'selected' : ''}>Hybrid</option>
                    </select>
                </div>
            </div>

            <div class="filter-options">
                <label class="checkbox-label">
                    <input type="checkbox" name="activeOnly" value="true" ${param.activeOnly ? 'checked' : ''}>
                    Show only active courses
                </label>
                
                <label class="checkbox-label">
                    <input type="checkbox" name="openOnly" value="true" ${param.openOnly ? 'checked' : ''}>
                    Show only courses accepting applications
                </label>
            </div>

            <button type="submit" class="submit-btn">Search</button>
        </form>
    </div>

    <c:if test="${not empty courses}">
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Course Title</th>
                        <th>Institution</th>
                        <th>Term</th>
                        <th>Schedule</th>
                        <th>Delivery</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td>${course.courseTitle}</td>
                            <td>${course.institutionName}</td>
                            <td>${course.term}</td>
                            <td>${course.schedule}</td>
                            <td>${course.deliveryMethod}</td>
                            <td>${course.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/course/view/${course.courseId}" 
                                   class="btn btn-primary">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<%@ include file="../../common/footer.jsp" %> 