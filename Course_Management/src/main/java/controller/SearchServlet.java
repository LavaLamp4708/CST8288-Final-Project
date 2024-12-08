package controller;

import model.Course;
import service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService(); // Initialize the service
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String term = request.getParameter("term");
        String schedule = request.getParameter("schedule");
        String deliveryMethod = request.getParameter("deliveryMethod");

        // Get all courses (you can add filtering logic here)
        List<Course> courses = courseService.getAllCourses();

        // Set attributes for display in JSP
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}
