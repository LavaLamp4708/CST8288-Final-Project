package AcademicExchangePlatform.servlet;

import AcademicExchangePlatform.model.Course;
import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.service.CourseService;
import AcademicExchangePlatform.dbenum.CourseStatus;
import AcademicExchangePlatform.dbenum.DeliveryMethod;
import AcademicExchangePlatform.dbenum.Schedule;
import AcademicExchangePlatform.dbenum.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CourseServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null || !user.getUserType().equals(UserType.INSTITUTION)) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        System.out.println("DEBUG: CourseServlet pathInfo: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/manage")) {
            handleManageCourses(request, response);
        } else if ("/create".equals(pathInfo)) {
            request.getRequestDispatcher("/WEB-INF/views/course/form.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/view/")) {
            handleViewCourse(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleManageCourses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String statusFilter = request.getParameter("status");
        String termFilter = request.getParameter("term");
        
        System.out.println("DEBUG: Handling manage courses");
        System.out.println("DEBUG: User ID: " + user.getUserId());
        System.out.println("DEBUG: Status Filter: " + statusFilter);
        System.out.println("DEBUG: Term Filter: " + termFilter);
        
        // Get courses for the institution
        List<Course> courses = courseService.getCoursesByInstitution(user.getUserId());
        System.out.println("DEBUG: Retrieved courses: " + courses);
        
        // Filter courses if status parameter is present
        if (statusFilter != null && !statusFilter.isEmpty()) {
            CourseStatus status = CourseStatus.valueOf(statusFilter);
            courses = courses.stream()
                           .filter(c -> c.getStatus() == status)
                           .collect(Collectors.toList());
        }
        
        // Filter courses if term parameter is present
        if (termFilter != null && !termFilter.isEmpty()) {
            courses = courses.stream()
                           .filter(c -> termFilter.equals(c.getTerm()))
                           .collect(Collectors.toList());
        }
        
        // Set attributes needed by manage.jsp
        request.setAttribute("courses", courses);
        request.setAttribute("terms", courseService.getAvailableTerms());
        
        // Forward to manage.jsp
        request.getRequestDispatcher("/WEB-INF/views/course/manage.jsp").forward(request, response);
    }

    private void handleViewCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int courseId = Integer.parseInt(request.getPathInfo().substring(6));
            Course course = courseService.getCourseById(courseId);
            if (course != null) {
                request.setAttribute("course", course);
                request.getRequestDispatcher("/WEB-INF/views/course/viewCourse.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !user.getUserType().equals(UserType.INSTITUTION)) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        try {
            Course course = createCourseFromRequest(request);
            course.setInstitutionId(user.getUserId());
            courseService.createCourse(course);
            response.sendRedirect(request.getContextPath() + "/course/manage");
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/course/manage.jsp").forward(request, response);
        }
    }

    private Course createCourseFromRequest(HttpServletRequest request) throws ParseException {
        Course course = new Course();
        course.setCourseTitle(request.getParameter("courseTitle"));
        course.setCourseCode(request.getParameter("courseCode"));
        course.setTerm(request.getParameter("term"));
        course.setSchedule(Schedule.valueOf(request.getParameter("schedule")));
        course.setDeliveryMethod(DeliveryMethod.valueOf(request.getParameter("deliveryMethod")));
        course.setOutline(request.getParameter("outline"));
        course.setPreferredQualifications(request.getParameter("preferredQualifications"));
        course.setCompensation(new BigDecimal(request.getParameter("compensation")));
        course.setStatus(CourseStatus.valueOf(request.getParameter("status")));
        
        String deadlineStr = request.getParameter("applicationDeadline");
        if (deadlineStr != null && !deadlineStr.isEmpty()) {
            course.setApplicationDeadline(dateFormat.parse(deadlineStr));
        }
        
        return course;
    }
}