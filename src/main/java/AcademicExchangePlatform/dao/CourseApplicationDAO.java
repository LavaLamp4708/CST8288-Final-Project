package AcademicExchangePlatform.dao;

import AcademicExchangePlatform.model.CourseApplication;
import AcademicExchangePlatform.dbenum.ApplicationStatus;
import java.util.List;

public interface CourseApplicationDAO {
    boolean createApplication(CourseApplication application);
    boolean updateApplicationStatus(int applicationId, ApplicationStatus status);
    CourseApplication getApplicationById(int applicationId);
    List<CourseApplication> getApplicationsByCourse(int courseId);
    List<CourseApplication> getApplicationsByProfessional(int professionalId);
    boolean withdrawApplication(int applicationId);
} 