package AcademicExchangePlatform.model;

public class AcademicInstitution extends User {
    private String institutionName;
    private String address;
    private String contactEmail;
    private String contactPhone;
    private String website;
    private String description;

    // Getters and Setters
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void completeProfile(String address, String contactEmail, String contactPhone, String website, String description) {
        this.address = address;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.website = website;
        this.description = description;
    }

    @Override
    public boolean isProfileComplete() {
        return institutionName != null && !institutionName.isEmpty() &&
               address != null && !address.isEmpty() &&
               contactEmail != null && !contactEmail.isEmpty() &&
               contactPhone != null && !contactPhone.isEmpty();
    }
}
