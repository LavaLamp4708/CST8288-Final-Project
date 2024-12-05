package AcademicExchangePlatform.dao;

/**
 *
 * @author Bob Paul
 */

import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.model.AcademicInstitution;
import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance;
    private Connection connection;

    private UserDAOImpl() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aep", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (email, password, userType) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user instanceof AcademicProfessional ? "AcademicProfessional" : "AcademicInstitution");
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                    if (user instanceof AcademicProfessional) {
                        AcademicProfessional prof = (AcademicProfessional) user;
                        sql = "INSERT INTO AcademicProfessionals (userId, firstName, lastName, position, expertise) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement profStmt = connection.prepareStatement(sql);
                        profStmt.setInt(1, user.getUserId());
                        profStmt.setString(2, prof.getFirstName());
                        profStmt.setString(3, prof.getLastName());
                        profStmt.setString(4, prof.getPosition());
                        profStmt.setString(5, String.join(",", prof.getExpertise()));
                        profStmt.executeUpdate();
                    } else if (user instanceof AcademicInstitution) {
                        AcademicInstitution inst = (AcademicInstitution) user;
                        sql = "INSERT INTO AcademicInstitutions (userId, institutionName, address) VALUES (?, ?, ?)";
                        PreparedStatement instStmt = connection.prepareStatement(sql);
                        instStmt.setInt(1, user.getUserId());
                        instStmt.setString(2, inst.getInstitutionName());
                        instStmt.setString(3, inst.getAddress());
                        instStmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        // Implementation to retrieve a user by email
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        // Implementation for updating user details
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        // Implementation for deleting user
        return false;
    }
}
