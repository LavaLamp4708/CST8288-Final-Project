<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Manage Applications" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <h2>Applications for ${course.courseTitle}</h2>
    
    <div class="filter-section">
        <form action="" method="GET" class="form-group">
            <div class="form-group">
                <label for="status">Filter by Status:</label>
                <select id="status" name="status" onchange="this.form.submit()">
                    <option value="">All Statuses</option>
                    <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                    <option value="ACCEPTED" ${param.status == 'ACCEPTED' ? 'selected' : ''}>Accepted</option>
                    <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                    <option value="WITHDRAWN" ${param.status == 'WITHDRAWN' ? 'selected' : ''}>Withdrawn</option>
                </select>
            </div>
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Applicant</th>
                    <th>Current Institution</th>
                    <th>Position</th>
                    <th>Applied Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${applications}" var="app">
                    <tr>
                        <td>${app.professional.firstName} ${app.professional.lastName}</td>
                        <td>${app.professional.currentInstitution}</td>
                        <td>${app.professional.position}</td>
                        <td>${app.applicationDate}</td>
                        <td><span class="status-${fn:toLowerCase(app.status)}">${app.status}</span></td>
                        <td>
                            <button onclick="viewApplication(${app.applicationId})" 
                                    class="btn btn-secondary">View Details</button>
                            
                            <c:if test="${app.status == 'PENDING'}">
                                <button onclick="updateStatus(${app.applicationId}, 'ACCEPTED')" 
                                        class="btn btn-success">Accept</button>
                                <button onclick="updateStatus(${app.applicationId}, 'REJECTED')" 
                                        class="btn btn-danger">Reject</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Application Details Modal -->
<div id="applicationModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h3>Application Details</h3>
        <div id="applicationDetails"></div>
    </div>
</div>

<script>
function viewApplication(applicationId) {
    fetch(`${pageContext.request.contextPath}/application/details/${applicationId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('applicationDetails').innerHTML = `
                <div class="application-detail">
                    <h4>Cover Letter</h4>
                    <p>${data.coverLetter}</p>
                    
                    <h4>Additional Documents</h4>
                    <p>${data.additionalDocuments || 'None provided'}</p>
                </div>
            `;
            document.getElementById('applicationModal').style.display = 'block';
        });
}

function updateStatus(applicationId, status) {
    if (confirm('Are you sure you want to ' + status.toLowerCase() + ' this application?')) {
        fetch(`${pageContext.request.contextPath}/application/updateStatus`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `applicationId=${applicationId}&status=${status}`
        }).then(() => window.location.reload());
    }
}

// Modal close functionality
document.querySelector('.close').onclick = function() {
    document.getElementById('applicationModal').style.display = 'none';
}
</script>

<%@ include file="../../common/footer.jsp" %> 