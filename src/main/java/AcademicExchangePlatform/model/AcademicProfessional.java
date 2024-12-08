package AcademicExchangePlatform.model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AcademicProfessional extends User {
    private String firstName;
    private String lastName;
    private String currentInstitution;
    private String position;
    private String educationBackground;
    private List<String> expertise;
    private boolean profileComplete;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentInstitution() {
        return currentInstitution;
    }

    public void setCurrentInstitution(String currentInstitution) {
        this.currentInstitution = currentInstitution;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public boolean isProfileComplete() {
        return profileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        this.profileComplete = profileComplete;
    }

    public void completeProfile(String position, String currentInstitution, 
                              String educationBackground, List<String> expertise) {
        this.position = position;
        this.currentInstitution = currentInstitution;
        this.educationBackground = educationBackground;
        this.expertise = new ArrayList<>(expertise);
        this.profileComplete = true;
    }
}
