package AcademicExchangePlatform.model;

/**
 *
 * @author Bob Paul
 */

public class AcademicInstitution extends User {
    private String institutionName;
    private String address;

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void completeProfile(String address) {
        this.address = address;
    }
}
