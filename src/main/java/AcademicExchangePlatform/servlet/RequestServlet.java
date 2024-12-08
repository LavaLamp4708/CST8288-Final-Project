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

@WebServlet("/Requests")
public class RequestServlet extends HttpServlet {

    private final RequestService requestService = RequestService.getInstance();

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

    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }

    private String getStringAttribute(HttpServletRequest request, String attributeName) {
        Object attribute = request.getAttribute(attributeName);
        if (attribute == null || !(attribute instanceof String)) {
            throw new IllegalArgumentException("Invalid or missing attribute: " + attributeName);
        }
        return (String) attribute;
    }

    private String determineTargetPage(String userType) {
        if ("AcademicProfessional".equals(userType)) {
            return "/WEB-INF/views/request/myRequests.jsp";
        } else if ("AcademicInstitution".equals(userType)) {
            return "/WEB-INF/views/decision/manage.jsp";
        }
        return null;
    }

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

    private List<Request> filterAcceptedOnly(List<Request> requests){
        List<Request> filtered = new ArrayList<Request>();
        for(Request acceptedRequest : requests){
            if(acceptedRequest.getStatus().equals("ACCEPTED")) filtered.add(acceptedRequest);
        }
        return filtered;
    }

    private List<Request> filterRejectedOnly(List<Request> requests){
        List<Request> filtered = new ArrayList<Request>();
        for(Request acceptedRequest : requests){
            if(acceptedRequest.getStatus().equals("REJECTED")) filtered.add(acceptedRequest);
        }
        return filtered;
    }

    private List<Request> filterPendingOnly(List<Request> requests){
        List<Request> filtered = new ArrayList<Request>();
        for(Request acceptedRequest : requests){
            if(acceptedRequest.getStatus().equals("PENDING")) filtered.add(acceptedRequest);
        }
        return filtered;
    }

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

    private void handleCancelAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ClassNotFoundException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        this.requestService.cancelRequestById(requestId);
        response.sendRedirect("/Requests");
    }

    private void handleRequestStatusChange(HttpServletRequest request, HttpServletResponse response, String status)
            throws IOException, SQLException, ClassNotFoundException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        this.requestService.handleRequest(requestId, status);
        response.sendRedirect("/Requests");
    }

    /**
     * doPost
     * @param request
     * @param response
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
     * doGet
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        //testing params
        request.setAttribute("userType", "AcademicProfessional");
        request.setAttribute("userId", 2);

        try {
            String userType = getStringAttribute(request, "userType");
            int userId = getIntFromAttribute(request, "userId");
            int courseId = getIntFromAttribute(request, "courseId");

            if (userId == -1) return;
            if ("AcademicInsytitution".equals(userType) && courseId == -1) return;

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
