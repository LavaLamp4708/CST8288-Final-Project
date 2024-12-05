package AcademicExchangePlatform.servlet;

/**
 *
 * @author Bob Paul
 */

import AcademicExchangePlatform.dao.UserDAOImpl;
import AcademicExchangePlatform.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserDAOImpl userDAO = UserDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Invalid email or password");
        }
    }
}
