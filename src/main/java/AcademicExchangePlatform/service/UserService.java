package AcademicExchangePlatform.service;

import AcademicExchangePlatform.dao.UserDAO;
import AcademicExchangePlatform.dao.UserDAOImpl;
import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.model.AcademicInstitution;
import AcademicExchangePlatform.model.AcademicProfessional;
import java.util.Date;
import java.util.List;

public class UserService {
    private static UserService instance;
    
    private UserService() {
        this.userDAO = UserDAOImpl.getInstance();
    }
    
    public static UserService getInstance() {
        if (instance == null) {
            synchronized(UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    private final UserDAO userDAO;

    public boolean register(User user) {
        if (!validateUserFields(user)) {
            return false;
        }

        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }

        return userDAO.addUser(user);
    }

    public User login(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password) && "ACTIVE".equals(user.getStatus())) {
            user.setLastLogin(new Date());
            userDAO.updateUser(user);
            return user;
        }
        return null;
    }

    public boolean updateProfile(User user) {
        return userDAO.updateUser(user);
    }

    public List<AcademicInstitution> getAllInstitutions() {
        return userDAO.getAllInstitutions();
    }

    private boolean validateUserFields(User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return false;
        }

        if (user instanceof AcademicProfessional) {
            return validateProfessional((AcademicProfessional) user);
        } else if (user instanceof AcademicInstitution) {
            return validateInstitution((AcademicInstitution) user);
        }
        return false;
    }

    private boolean validateProfessional(AcademicProfessional professional) {
        return professional.getFirstName() != null && !professional.getFirstName().isEmpty() &&
               professional.getLastName() != null && !professional.getLastName().isEmpty() &&
               professional.getCurrentInstitution() != null && !professional.getCurrentInstitution().isEmpty() &&
               professional.getPosition() != null && !professional.getPosition().isEmpty();
    }

    private boolean validateInstitution(AcademicInstitution institution) {
        return institution.getInstitutionName() != null && !institution.getInstitutionName().isEmpty();
    }

    public AcademicProfessional getProfessionalById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user instanceof AcademicProfessional) {
            return (AcademicProfessional) user;
        }
        return null;
    }
}
