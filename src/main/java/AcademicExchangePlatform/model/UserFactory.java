package AcademicExchangePlatform.model;
import AcademicExchangePlatform.dbenum.UserType;

public class UserFactory {
    public static User createUser(UserType type) {
        switch (type) {
            case PROFESSIONAL:
                return new AcademicProfessional();
            case INSTITUTION:
                return new AcademicInstitution();
            default:
                throw new IllegalArgumentException("Invalid user type");
        }
    }
}
