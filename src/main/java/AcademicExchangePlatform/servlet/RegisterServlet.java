package AcademicExchangePlatform.servlet;

import AcademicExchangePlatform.dbenum.UserType;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.model.AcademicInstitution;
import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Load institution list for dropdown
        request.setAttribute("institutions", userService.getAllInstitutions());
        request.getRequestDispatcher("/WEB-INF/views/auth/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get common parameters
            String email = getRequiredParameter(request, "email");
            String password = getRequiredParameter(request, "password");
            String userTypeValue = getRequiredParameter(request, "userType");

            UserType userType = UserType.fromValue(userTypeValue);
            User user;

            if (userType == UserType.PROFESSIONAL) {
                user = createProfessionalUser(request);
            } else if (userType == UserType.INSTITUTION) {
                user = createInstitutionUser(request);
            } else {
                throw new IllegalArgumentException("Invalid user type");
            }

            user.setEmail(email);
            user.setPassword(password);
            user.setUserType(userType);

            if (userService.register(user)) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else {
                setError(request, "Registration failed");
                doGet(request, response);
            }
        } catch (IllegalArgumentException e) {
            setError(request, "Invalid input: " + e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            setError(request, "System error: " + e.getMessage());
            doGet(request, response);
        }
    }

    private String getRequiredParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: " + paramName);
        }
        return value.trim();
    }

    private void setError(HttpServletRequest request, String message) {
        request.setAttribute("error", message);
    }

    private AcademicProfessional createProfessionalUser(HttpServletRequest request) {
        AcademicProfessional professional = new AcademicProfessional();
        professional.setFirstName(getRequiredParameter(request, "firstName"));
        professional.setLastName(getRequiredParameter(request, "lastName"));
        professional.setCurrentInstitution(getRequiredParameter(request, "currentInstitution"));
        professional.setPosition(getRequiredParameter(request, "academicPosition"));
        return professional;
    }

    private AcademicInstitution createInstitutionUser(HttpServletRequest request) {
        AcademicInstitution institution = new AcademicInstitution();
        institution.setInstitutionName(getRequiredParameter(request, "institutionName"));
        institution.setAddress(getRequiredParameter(request, "address"));
        institution.setContactEmail(getRequiredParameter(request, "contactEmail"));
        return institution;
    }
}
