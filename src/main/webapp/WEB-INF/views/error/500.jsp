<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Server Error" scope="request"/>
<%@ include file="../../common/header.jsp" %>

<div class="error-container">
    <div class="error-content">
        <h1>500</h1>
        <h2>Server Error</h2>
        <p>Something went wrong on our end. Please try again later.</p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go Home</a>
            <a href="javascript:history.back()" class="btn btn-secondary">Go Back</a>
        </div>
    </div>
</div>

<%@ include file="../../common/footer.jsp" %> 