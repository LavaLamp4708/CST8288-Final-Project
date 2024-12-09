package AcademicExchangePlatform.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.service.UserService;
import AcademicExchangePlatform.dbenum.UserType;

public class HomeServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("DEBUG: HomeServlet doGet started");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            System.out.println("DEBUG: User is null, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        System.out.println("DEBUG: User type: " + user.getUserType());
        System.out.println("DEBUG: User class: " + user.getClass().getName());
        System.out.println("DEBUG: Session ID: " + session.getId());
        System.out.println("DEBUG: Context Path: " + request.getContextPath());
        
        // Set common attributes
        request.setAttribute("user", user);
        request.setAttribute("unreadNotifications", 0);

        // Set user-specific attributes based on user type
        if (user.getUserType().equals(UserType.PROFESSIONAL)) {
            System.out.println("DEBUG: Setting PROFESSIONAL attributes");
            request.setAttribute("pendingApplications", 0);
            request.setAttribute("acceptedApplications", 0);
        } else if (user.getUserType().equals(UserType.INSTITUTION)) {
            System.out.println("DEBUG: Setting INSTITUTION attributes");
            request.setAttribute("activeCourses", 0);
            request.setAttribute("pendingApplications", 0);
            request.setAttribute("recentApplications", null);
        }
        
        System.out.println("DEBUG: Forwarding to dashboard.jsp");
        request.getRequestDispatcher("/WEB-INF/views/home/dashboard.jsp").forward(request, response);
    }
} 