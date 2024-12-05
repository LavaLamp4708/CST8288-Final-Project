package AcademicExchangePlatform.servlet;

/**
 *
 * @author Bob Paul
 */

import AcademicExchangePlatform.model.*;
import AcademicExchangePlatform.dao.UserDAOImpl;
import AcademicExchangePlatform.factory.UserFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserDAOImpl userDAO = UserDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userType = request.getParameter("userType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = UserFactory.createUser(userType);
        user.setEmail(email);
        user.setPassword(password);

        if (user instanceof AcademicProfessional) {
            ((AcademicProfessional) user).setFirstName(request.getParameter("firstName"));
            ((AcademicProfessional) user).setLastName(request.getParameter("lastName"));
        } else if (user instanceof AcademicInstitution) {
            ((AcademicInstitution) user).setInstitutionName(request.getParameter("institutionName"));
            ((AcademicInstitution) user).setAddress(request.getParameter("address"));
        }

        boolean isRegistered = userDAO.addUser(user);
        if (isRegistered) {
            response.sendRedirect("success.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
