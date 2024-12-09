package AcademicExchangePlatform.service;

import AcademicExchangePlatform.dao.CourseApplicationDAO;
import AcademicExchangePlatform.dao.CourseApplicationDAOImpl;
import AcademicExchangePlatform.dao.CourseDAO;
import AcademicExchangePlatform.dao.CourseDAOImpl;
import AcademicExchangePlatform.model.Course;
import AcademicExchangePlatform.model.CourseApplication;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.dbenum.ApplicationStatus;
import AcademicExchangePlatform.dbenum.CourseStatus;

import java.util.Date;
import java.util.List;

public class CourseApplicationService {
    private static CourseApplicationService instance;
    private final CourseApplicationDAO applicationDAO;
    private final CourseDAO courseDAO;

    private CourseApplicationService() {
        this.applicationDAO = new CourseApplicationDAOImpl();
        this.courseDAO = new CourseDAOImpl();
    }

    public static CourseApplicationService getInstance() {
        if (instance == null) {
            instance = new CourseApplicationService();
        }
        return instance;
    }

    public boolean applyForCourse(CourseApplication application, AcademicProfessional professional) {
        Course course = courseDAO.getCourseById(application.getCourseId());
        
        if (!canApplyToCourse(professional, course)) {
            if (!professional.isProfileComplete()) {
                NotificationService.getInstance().notifyProfileIncomplete(professional.getUserId());
            }
            return false;
        }
        
        boolean created = applicationDAO.createApplication(application);
        if (created) {
            NotificationService.getInstance().notifyNewApplication(application, course);
        }
        return created;
    }

    public boolean withdrawApplication(int applicationId, int professionalId) {
        CourseApplication application = applicationDAO.getApplicationById(applicationId);
        
        if (application == null || application.getProfessionalId() != professionalId) {
            return false;
        }

        if (application.getStatus() != ApplicationStatus.PENDING) {
            return false;
        }

        return applicationDAO.withdrawApplication(applicationId);
    }

    public boolean updateApplicationStatus(int applicationId, ApplicationStatus status, int institutionId) {
        CourseApplication application = applicationDAO.getApplicationById(applicationId);
        if (application == null) {
            return false;
        }

        Course course = courseDAO.getCourseById(application.getCourseId());
        if (course == null || course.getInstitutionId() != institutionId) {
            return false;
        }

        return applicationDAO.updateApplicationStatus(applicationId, status);
    }

    public List<CourseApplication> getApplicationsByCourse(int courseId, int institutionId) {
        Course course = courseDAO.getCourseById(courseId);
        if (course == null || course.getInstitutionId() != institutionId) {
            return null;
        }
        return applicationDAO.getApplicationsByCourse(courseId);
    }

    public List<CourseApplication> getApplicationsByProfessional(int professionalId) {
        return applicationDAO.getApplicationsByProfessional(professionalId);
    }

    private boolean canApplyToCourse(AcademicProfessional professional, Course course) {
        if (course == null || !professional.isProfileComplete()) {
            return false;
        }
        
        Date now = new Date();
        return course.getStatus() == CourseStatus.ACTIVE && 
               course.getApplicationDeadline().after(now);
    }

    private boolean validateApplication(CourseApplication application) {
        return application != null &&
               application.getCoverLetter() != null && 
               !application.getCoverLetter().trim().isEmpty();
    }
} 