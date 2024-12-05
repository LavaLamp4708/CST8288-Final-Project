package AcademicExchangePlatform.dao;

/**
 *
 * @author Bob Paul
 */

import AcademicExchangePlatform.model.User;

public interface UserDAO {
    boolean addUser(User user);
    User getUserByEmail(String email);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
