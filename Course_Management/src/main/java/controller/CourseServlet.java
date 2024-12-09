package controller;

import model.Course;
import model.CourseBuilder;
import service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService(); // Initialize the service
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createCourse(request, response);
        } else if ("update".equals(action)) {
            updateCourse(request, response);
        } else if ("delete".equals(action)) {
            deleteCourse(request, response);
        }
    }

    private void createCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Course course = new CourseBuilder()
                    .setCourseTitle(request.getParameter("courseTitle"))
                    .setCourseCode(request.getParameter("courseCode"))
                    .setTerm(request.getParameter("term"))
                    .setSchedule(request.getParameter("schedule"))
                    .setDeliveryMethod(request.getParameter("deliveryMethod"))
                    .setOutline(request.getParameter("outline"))
                    .setPreferredQualifications(request.getParameter("preferredQualifications"))
                    .setCompensation(Double.parseDouble(request.getParameter("compensation")))
                    .build();

            boolean isCreated = courseService.createCourse(course);
            if (isCreated) {
                response.sendRedirect("course.jsp?success=Course created successfully!");
            } else {
                response.sendRedirect("course.jsp?error=Failed to create course.");
            }
        } catch (Exception e) {
            response.sendRedirect("course.jsp?error=Invalid input data.");
        }
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Course course = new CourseBuilder()
                    .setCourseId(Integer.parseInt(request.getParameter("courseId")))
                    .setCourseTitle(request.getParameter("courseTitle"))
                    .setCourseCode(request.getParameter("courseCode"))
                    .setTerm(request.getParameter("term"))
                    .setSchedule(request.getParameter("schedule"))
                    .setDeliveryMethod(request.getParameter("deliveryMethod"))
                    .setOutline(request.getParameter("outline"))
                    .setPreferredQualifications(request.getParameter("preferredQualifications"))
                    .setCompensation(Double.parseDouble(request.getParameter("compensation")))
                    .build();

            boolean isUpdated = courseService.updateCourse(course);
            if (isUpdated) {
                response.sendRedirect("course.jsp?success=Course updated successfully!");
            } else {
                response.sendRedirect("course.jsp?error=Failed to update course.");
            }
        } catch (Exception e) {
            response.sendRedirect("course.jsp?error=Invalid input data.");
        }
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            boolean isDeleted = courseService.deleteCourse(courseId);
            if (isDeleted) {
                response.sendRedirect("course.jsp?success=Course deleted successfully!");
            } else {
                response.sendRedirect("course.jsp?error=Failed to delete course.");
            }
        } catch (Exception e) {
            response.sendRedirect("course.jsp?error=Invalid course ID.");
        }
    }
}
