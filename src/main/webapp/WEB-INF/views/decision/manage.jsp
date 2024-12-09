<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Decisions Management" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="container">
    <h2>Decisions Management</h2>
    
    <div class="filter-section">
        <form action="" method="GET" class="form-group">
            <div class="form-group">
                <label for="status">Filter by Status:</label>
                <select id="status" name="status" onchange="this.form.submit()">
                    <option value="">All Statuses</option>
                    <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                    <option value="ACCEPTED" ${param.status == 'ACCEPTED' ? 'selected' : ''}>Accepted</option>
                    <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                </select>
            </div>
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Course Title</th>
                    <th>Request Date</th>
                    <th>Decision Date</th>
                    <th>Current Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="decision" items="${decisions}">
                    <tr>
                        <td>${decision.courseTitle}</td>
                        <td>${decision.requestDate}</td>
                        <td>${decision.decisionDate}</td>
                        <td><span class="status-${fn:toLowerCase(decision.status)}">${decision.status}</span></td>
                        <td class="action-buttons">
                            <c:if test="${decision.status == 'PENDING'}">
                                <button onclick="updateDecision(${decision.decisionId}, 'ACCEPTED')" 
                                        class="btn btn-success">Accept</button>
                                <button onclick="updateDecision(${decision.decisionId}, 'REJECTED')" 
                                        class="btn btn-danger">Reject</button>
                            </c:if>
                            <button onclick="viewDetails(${decision.decisionId})" 
                                    class="btn btn-secondary">View Details</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}&status=${param.status}" class="btn btn-secondary">&laquo; Previous</a>
        </c:if>
        <c:if test="${hasNextPage}">
            <a href="?page=${currentPage + 1}&status=${param.status}" class="btn btn-secondary">Next &raquo;</a>
        </c:if>
    </div>
</div>

<!-- Decision Details Modal -->
<div id="decisionModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h3>Decision Details</h3>
        <div id="decisionDetails"></div>
    </div>
</div>

<script>
function updateDecision(decisionId, status) {
    if (confirm('Are you sure you want to ' + status.toLowerCase() + ' this decision?')) {
        fetch('${pageContext.request.contextPath}/requests/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `requestId=${decisionId}&status=${status}`
        }).then(() => window.location.reload());
    }
}

function viewDetails(decisionId) {
    fetch(`${pageContext.request.contextPath}/requests/details/${decisionId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('decisionDetails').innerHTML = `
                <div class="decision-detail">
                    <p><strong>Course:</strong> ${data.courseTitle}</p>
                    <p><strong>Requester:</strong> ${data.requesterName}</p>
                    <p><strong>Request Date:</strong> ${data.requestDate}</p>
                    <p><strong>Status:</strong> ${data.status}</p>
                    <p><strong>Notes:</strong> ${data.notes || 'No notes available'}</p>
                </div>
            `;
            document.getElementById('decisionModal').style.display = 'block';
        });
}

// Modal close functionality
document.querySelector('.close').onclick = function() {
    document.getElementById('decisionModal').style.display = 'none';
}
</script>

<%@ include file="../../common/footer.jsp" %> 