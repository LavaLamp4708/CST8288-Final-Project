package AcademicExchangePlatform.servlet;

import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.model.AcademicInstitution;
import AcademicExchangePlatform.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ProfileServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String jspPath = determineJspPath(user);
        request.setAttribute("user", user);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            updateUserProfile(request, user);
            if (userService.updateProfile(user)) {
                request.setAttribute("success", "Profile updated successfully");
            } else {
                request.setAttribute("error", "Failed to update profile");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        doGet(request, response);
    }

    private String determineJspPath(User user) {
        if (user instanceof AcademicProfessional) {
            return "/WEB-INF/professional-profile.jsp";
        } else if (user instanceof AcademicInstitution) {
            return "/WEB-INF/institution-profile.jsp";
        }
        return "/WEB-INF/error.jsp";
    }

    private void updateUserProfile(HttpServletRequest request, User user) {
        if (user instanceof AcademicProfessional) {
            updateProfessionalProfile(request, (AcademicProfessional) user);
        } else if (user instanceof AcademicInstitution) {
            updateInstitutionProfile(request, (AcademicInstitution) user);
        }
    }

    private void updateProfessionalProfile(HttpServletRequest request, AcademicProfessional professional) {
        String position = request.getParameter("position");
        String currentInstitution = request.getParameter("currentInstitution");
        String educationBackground = request.getParameter("educationBackground");
        String expertiseStr = request.getParameter("expertise");
        List<String> expertise = Arrays.asList(expertiseStr.split(","));

        professional.completeProfile(position, currentInstitution, educationBackground, expertise);
    }

    private void updateInstitutionProfile(HttpServletRequest request, AcademicInstitution institution) {
        String address = request.getParameter("address");
        String contactEmail = request.getParameter("contactEmail");
        String contactPhone = request.getParameter("contactPhone");
        String website = request.getParameter("website");
        String description = request.getParameter("description");
        
        institution.completeProfile(address, contactEmail, contactPhone, website, description);
    }
}
