package AcademicExchangePlatform.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import AcademicExchangePlatform.model.Request;
import AcademicExchangePlatform.service.RequestService;

/**
 * The {@code RequestServlet} class handles HTTP requests related to managing and displaying
 * academic requests for users in the Academic Exchange Platform. It supports retrieving,
 * filtering, paginating, and updating request statuses for both academic professionals
 * and academic institutions.
 * 
 * <p>
 * This servlet manages:
 * </p>
 * <ul>
 *     <li>Handling user requests (GET and POST).</li>
 *     <li>Dispatching pages based on user type.</li>
 *     <li>Filtering and paginating requests.</li>
 *     <li>Performing actions such as accepting, rejecting, and canceling requests.</li>
 * </ul>
 * 
 * <p>
 * Example URL patterns handled:
 * </p>
 * <ul>
 *     <li><code>/Requests</code></li>
 * </ul>
 * 
 * <p>
 * Note: This servlet relies on the {@link AcademicExchangePlatform.service.RequestService} for
 * data operations and business logic.
 * </p>
 * 
 * <p><b>Author:</b> Peter Stainforth</p>
 * 
 * @see AcademicExchangePlatform.service.RequestService
 * @see HttpServlet
 */
@WebServlet("/Requests")
public class RequestServlet extends HttpServlet {

    private final RequestService requestService = RequestService.getInstance();

    /**
     * Retrieves an integer value from a request attribute.
     * 
     * @param request the HttpServletRequest object.
     * @param attributeName the name of the attribute to retrieve.
     * @return the integer value of the attribute, or -1 if the attribute is missing or not a valid integer.
     * @throws IOException if an input or output exception occurs.
     */
    private int getIntFromAttribute(HttpServletRequest request, String attributeName) throws IOException {
        Object attribute = request.getAttribute(attributeName);
        if(attribute == null){
            return -1;
        }
        int value = -1;
        try{
            value = Integer.parseInt((String)attribute);
        } catch (NumberFormatException e) {
            return -1;
        }
        return value;
    }

    /**
     * Sends an HTTP error response with the specified status code and message.
     * 
     * @param response the HttpServletResponse object.
     * @param statusCode the HTTP status code to send.
     * @param message the error message to include in the response.
     * @throws IOException if an input or output exception occurs.
     */
    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }

    /**
     * Retrieves a string value from a request attribute.
     * 
     * @param request the HttpServletRequest object.
     * @param attributeName the name of the attribute to retrieve.
     * @return the string value of the attribute.
     * @throws IllegalArgumentException if the attribute is missing or not a valid string.
     */
    private String getStringAttribute(HttpServletRequest request, String attributeName) {
        Object attribute = request.getAttribute(attributeName);
        if (attribute == null || !(attribute instanceof String)) {
            throw new IllegalArgumentException("Invalid or missing attribute: " + attributeName);
        }
        return (String) attribute;
    }

    /**
     * Determines the target page based on the user type.
     * 
     * @param userType the type of user (e.g., "AcademicProfessional" or "AcademicInstitution").
     * @return the target page as a string, or null if the user type is invalid.
     */
    private String determineTargetPage(String userType) {
        if ("AcademicProfessional".equals(userType)) {
            return "/WEB-INF/views/request/myRequests.jsp";
        } else if ("AcademicInstitution".equals(userType)) {
            return "/WEB-INF/views/decision/manage.jsp";
        }
        return null;
    }

    /**
     * Paginates a list of requests based on the "page" parameter in the HTTP request.
     * 
     * @param request the HttpServletRequest object.
     * @param requests the list of requests to paginate.
     * @return a sublist of requests for the current page.
     */
    private List<Request> paginateRequests(HttpServletRequest request, List<Request> requests) {
        int pageLimit = (requests.size() + 9) / 10;
        int page = 0;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ignored) {
        }

        if (page >= pageLimit) {
            page = pageLimit - 1;
        }

        int start = page * 10;
        int end = Math.min(start + 10, requests.size());
        return new ArrayList<>(requests.subList(start, end));
    }

    /**
     * Filters a list of requests to include only those with the "ACCEPTED" status.
     * 
     * @param requests the list of requests to filter.
     * @return a filtered list containing only accepted requests.
     */
    private List<Request> filterAcceptedOnly(List<Request> requests){
        List<Request> filtered = new ArrayList<Request>();
        for(Request acceptedRequest : requests){
            if(acceptedRequest.getStatus().equals("ACCEPTED")) filtered.add(acceptedRequest);
        }
        return filtered;
    }

    /**
     * Filters a list of requests to include only those with the "ACCEPTED" status.
     * 
     * @param requests the list of requests to filter.
     * @return a filtered list containing only accepted requests.
     */
    private List<Request> filterRejectedOnly(List<Request> requests){
        List<Request> filtered = new ArrayList<Request>();
        for(Request acceptedRequest : requests){
            if(acceptedRequest.getStatus().equals("REJECTED")) filtered.add(acceptedRequest);
        }
        return filtered;
    }

    /**
     * Filters a list of requests to include only those with the "PENDING" status.
     * 
     * @param requests the list of requests to filter.
     * @return a filtered list containing only pending requests.
     */
    private List<Request> filterPendingOnly(List<Request> requests){
        List<Request> filtered = new ArrayList<Request>();
        for(Request acceptedRequest : requests){
            if(acceptedRequest.getStatus().equals("PENDING")) filtered.add(acceptedRequest);
        }
        return filtered;
    }

    /**
     * Dispatches the request to the appropriate page based on user type and request data.
     * 
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param userId the ID of the user.
     * @param courseId the ID of the course (only for AcademicInstitution users).
     * @param userType the type of user (e.g., "AcademicProfessional" or "AcademicInstitution").
     * @param targetPage the target page to forward the request.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an input or output exception occurs.
     */
    private void dispatchPage(HttpServletRequest request, HttpServletResponse response, int userId, int courseId, String userType, String targetPage) throws ServletException, IOException {
        switch(userType){
            case "AcademicInstitution":
                try{
                    List<Request> requestsByCourseId = this.requestService.getRequestByCourse(courseId);
                    List<Request> paginatedRequests = paginateRequests(request, requestsByCourseId);
                    request.setAttribute("requestsByCourseId", paginatedRequests);
                    request.getRequestDispatcher(targetPage);
                } catch (ClassNotFoundException | SQLException e){
                    sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
            break;
            case "AcademicProfessional":
                try{
                    List<Request> requestsByUserId = this.requestService.getRequestByUserId(userId);

                    //filter list
                    if(request.getParameter("status") != null){
                        String status = request.getParameter("status");
                        if(status.equals("ACCEPTED")) requestsByUserId = filterAcceptedOnly(requestsByUserId);
                        if(status.equals("REJECTED")) requestsByUserId = filterRejectedOnly(requestsByUserId);
                        if(status.equals("PENDING")) requestsByUserId = filterPendingOnly(requestsByUserId);
                    }

                    List<Request> paginatedRequests = paginateRequests(request, requestsByUserId);
                    request.setAttribute("requestsByUserId", paginatedRequests);
                    request.getRequestDispatcher("/WEB-INF/views/request/myRequests.jsp").forward(request, response);
                } catch (ClassNotFoundException | SQLException e){
                    sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
            break;
        }
    }

    /**
     * Handles the cancel action for a request.
     * 
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @throws IOException if an input or output exception occurs.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the database driver class is not found.
     */
    private void handleCancelAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ClassNotFoundException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        this.requestService.cancelRequestById(requestId);
        response.sendRedirect("/Requests");
    }

    /**
     * Handles a status change (accept/reject) for a request.
     * 
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param status the new status to set for the request ("Accepted" or "Rejected").
     * @throws IOException if an input or output exception occurs.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the database driver class is not found.
     */
    private void handleRequestStatusChange(HttpServletRequest request, HttpServletResponse response, String status)
            throws IOException, SQLException, ClassNotFoundException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        this.requestService.handleRequest(requestId, status);
        response.sendRedirect("/Requests");
    }

    /**
     * Handles POST requests to perform actions like canceling or changing request statuses.
     * 
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @throws IOException if an input or output exception occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        try {
            if ("cancel".equals(action)) {
                handleCancelAction(request, response);
            } else if ("accept".equals(action)) {
                handleRequestStatusChange(request, response, "Accepted");
            } else if ("reject".equals(action)) {
                handleRequestStatusChange(request, response, "Rejected");
            } else {
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
    
    /**
     * Handles GET requests to retrieve and display requests for a user.
     * 
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @throws IOException if an input or output exception occurs.
     * @throws ServletException if a servlet-specific error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            String userType = getStringAttribute(request, "userType");
            int userId = getIntFromAttribute(request, "userId");
            int courseId = getIntFromAttribute(request, "courseId");

            if (userId == -1) return;
            if ("AcademicInstitution".equals(userType) && courseId == -1) return;

            String targetPage = determineTargetPage(userType);
            if (targetPage == null) {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Target page cannot be determined.");
                return;
            }

            dispatchPage(request, response, userId, courseId, userType, targetPage);

        } catch (IllegalArgumentException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
