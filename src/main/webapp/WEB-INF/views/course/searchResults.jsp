<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Course Search Results" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <div class="search-results-header">
        <h2>Search Results</h2>
        <p>${courses.size()} courses found</p>
    </div>

    <div class="active-filters">
        <c:if test="${not empty param.keyword || not empty param.term || not empty param.deliveryMethod}">
            <h3>Active Filters:</h3>
            <div class="filter-tags">
                <c:if test="${not empty param.keyword}">
                    <span class="filter-tag">
                        Keyword: ${param.keyword}
                        <a href="?${pageContext.request.queryString.replace('keyword=' + param.keyword, '')}" 
                           class="remove-filter">&times;</a>
                    </span>
                </c:if>
                <c:if test="${not empty param.term}">
                    <span class="filter-tag">
                        Term: ${param.term}
                        <a href="?${pageContext.request.queryString.replace('term=' + param.term, '')}" 
                           class="remove-filter">&times;</a>
                    </span>
                </c:if>
                <c:if test="${not empty param.deliveryMethod}">
                    <span class="filter-tag">
                        Delivery: ${param.deliveryMethod}
                        <a href="?${pageContext.request.queryString.replace('deliveryMethod=' + param.deliveryMethod, '')}" 
                           class="remove-filter">&times;</a>
                    </span>
                </c:if>
            </div>
        </c:if>
    </div>

    <div class="search-results-grid">
        <c:forEach items="${courses}" var="course">
            <div class="course-card">
                <div class="course-header">
                    <h3>${course.courseTitle}</h3>
                    <span class="institution-name">${course.institutionName}</span>
                </div>
                
                <div class="course-info">
                    <p><strong>Term:</strong> ${course.term}</p>
                    <p><strong>Schedule:</strong> ${course.schedule}</p>
                    <p><strong>Delivery:</strong> ${course.deliveryMethod}</p>
                    <p><strong>Compensation:</strong> $${course.compensation}</p>
                    <p><strong>Deadline:</strong> ${course.applicationDeadline}</p>
                </div>
                
                <div class="course-preview">
                    <p>${fn:substring(course.outline, 0, 150)}...</p>
                </div>
                
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/course/view/${course.courseId}" 
                       class="btn btn-primary">View Details</a>
                    
                    <c:if test="${userType == 'PROFESSIONAL' && course.status == 'ACTIVE'}">
                        <a href="${pageContext.request.contextPath}/course/view/${course.courseId}#apply" 
                           class="btn btn-secondary">Apply Now</a>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        
        <c:if test="${empty courses}">
            <div class="empty-state">
                <p>No courses found matching your criteria.</p>
                <p>Try adjusting your search filters or <a href="${pageContext.request.contextPath}/course/search">start a new search</a>.</p>
            </div>
        </c:if>
    </div>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}&${pageContext.request.queryString.replace('page=' + currentPage, '')}" 
               class="btn btn-secondary">&laquo; Previous</a>
        </c:if>
        <c:if test="${hasNextPage}">
            <a href="?page=${currentPage + 1}&${pageContext.request.queryString.replace('page=' + currentPage, '')}" 
               class="btn btn-secondary">Next &raquo;</a>
        </c:if>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 