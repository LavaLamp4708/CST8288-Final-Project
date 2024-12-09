package AcademicExchangePlatform.servlet;

import AcademicExchangePlatform.model.CourseApplication;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.service.CourseApplicationService;
import AcademicExchangePlatform.dbenum.ApplicationStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class CourseApplicationServlet extends HttpServlet {
    private final CourseApplicationService applicationService = CourseApplicationService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        switch (pathInfo) {
            case "/submit":
                handleSubmitApplication(request, response);
                break;
            case "/withdraw":
                handleWithdrawApplication(request, response);
                break;
            case "/updateStatus":
                handleUpdateStatus(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleSubmitApplication(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        AcademicProfessional professional = (AcademicProfessional) request.getSession().getAttribute("user");
        
        CourseApplication application = new CourseApplication();
        application.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        application.setProfessionalId(professional.getUserId());
        application.setCoverLetter(request.getParameter("coverLetter"));
        application.setAdditionalDocuments(request.getParameter("additionalDocs"));
        application.setApplicationDate(new Date());
        application.setStatus(ApplicationStatus.PENDING);

        boolean success = applicationService.applyForCourse(application, professional);
        
        if (success) {
            response.sendRedirect("/application/my-applications?success=true");
        } else {
            response.sendRedirect("/course/view?id=" + application.getCourseId() + "&error=true");
        }
    }

    private void handleWithdrawApplication(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        AcademicProfessional professional = (AcademicProfessional) request.getSession().getAttribute("user");
        
        boolean success = applicationService.withdrawApplication(applicationId, professional.getUserId());
        response.sendRedirect("/application/my-applications?withdrawn=" + success);
    }

    private void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        ApplicationStatus status = ApplicationStatus.valueOf(request.getParameter("status"));
        int institutionId = (int) request.getSession().getAttribute("institutionId");
        
        boolean success = applicationService.updateApplicationStatus(applicationId, status, institutionId);
        response.sendRedirect("/application/manage?updated=" + success);
    }
} 