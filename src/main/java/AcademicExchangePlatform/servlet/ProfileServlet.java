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
import java.util.ArrayList;


@WebServlet(urlPatterns = {"/profile", "/profile/*"})
public class ProfileServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
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
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        try {
            System.out.println("DEBUG: ProfileServlet doPost - User type: " + user.getUserType());
            System.out.println("DEBUG: ProfileServlet doPost - Session ID: " + request.getSession().getId());
            
            updateUserProfile(request, user);
            if (userService.updateProfile(user)) {
                request.getSession().setAttribute("successMessage", "Profile updated successfully");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update profile");
            }
        } catch (Exception e) {
            System.out.println("DEBUG: ProfileServlet doPost - Error: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }

    private String determineJspPath(User user) {
        if (user instanceof AcademicProfessional) {
            return "/WEB-INF/views/profile/professional-profile.jsp";
        } else if (user instanceof AcademicInstitution) {
            return "/WEB-INF/views/profile/institution-profile.jsp";
        }
        return "/WEB-INF/views/error.jsp";
    }

    private void updateUserProfile(HttpServletRequest request, User user) {
        if (user instanceof AcademicProfessional) {
            updateProfessionalProfile(request, (AcademicProfessional) user);
        } else if (user instanceof AcademicInstitution) {
            updateInstitutionProfile(request, (AcademicInstitution) user);
        }
    }

    private void updateProfessionalProfile(HttpServletRequest request, AcademicProfessional professional) {
        try {
            String position = request.getParameter("position");
            String currentInstitution = request.getParameter("currentInstitution");
            String educationBackground = request.getParameter("educationBackground");
            String[] expertiseArray = request.getParameterValues("expertise");
            List<String> expertise = new ArrayList<>();
            
            if (expertiseArray != null) {
                expertise = Arrays.asList(expertiseArray);
            }

            professional.completeProfile(position, currentInstitution, educationBackground, expertise);
            
            // Update the database with the new profile completion status
            userService.updateUser(professional);
            
            // Update the session attribute to reflect the changes
            request.getSession().setAttribute("user", professional);
            
            System.out.println("DEBUG: Profile update completed and persisted");
        } catch (Exception e) {
            System.out.println("DEBUG: Error in updateProfessionalProfile: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
