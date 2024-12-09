package AcademicExchangePlatform.servlet;

import AcademicExchangePlatform.model.CourseApplication;
import AcademicExchangePlatform.service.CourseApplicationService;
import AcademicExchangePlatform.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ManageApplicationsServlet extends HttpServlet {
    private final CourseApplicationService applicationService = CourseApplicationService.getInstance();
    private final CourseService courseService = CourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int institutionId = (int) request.getSession().getAttribute("institutionId");
        
        List<CourseApplication> applications = applicationService.getApplicationsByCourse(courseId, institutionId);
        request.setAttribute("applications", applications);
        request.setAttribute("course", courseService.getCourseById(courseId));
        
        request.getRequestDispatcher("/WEB-INF/views/application/manageApplications.jsp")
               .forward(request, response);
    }
} 