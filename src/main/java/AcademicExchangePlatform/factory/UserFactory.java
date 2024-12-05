package AcademicExchangePlatform.factory;

/**
 *
 * @author Bob Paul
 */

import AcademicExchangePlatform.model.User;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.model.AcademicInstitution;

public class UserFactory {
    public static User createUser(String userType) {
        if (userType.equalsIgnoreCase("AcademicProfessional")) {
            return new AcademicProfessional();
        } else if (userType.equalsIgnoreCase("AcademicInstitution")) {
            return new AcademicInstitution();
        }
        return null;
    }
}
