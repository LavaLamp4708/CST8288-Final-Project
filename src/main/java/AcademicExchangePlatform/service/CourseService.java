package AcademicExchangePlatform.service;

import AcademicExchangePlatform.dao.CourseDAO;
import AcademicExchangePlatform.dao.CourseDAOImpl;
import AcademicExchangePlatform.model.Course;
import AcademicExchangePlatform.model.AcademicProfessional;
import AcademicExchangePlatform.dbenum.CourseStatus;
import AcademicExchangePlatform.dbenum.DeliveryMethod;
import AcademicExchangePlatform.model.CourseSearchCriteria;
import AcademicExchangePlatform.service.UserService;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class CourseService {
    private static CourseService instance;
    private final CourseDAO courseDAO;

    private CourseService() {
        this.courseDAO = new CourseDAOImpl();
    }

    public static CourseService getInstance() {
        if (instance == null) {
            instance = new CourseService();
        }
        return instance;
    }

    public boolean createCourse(Course course) {
        try {
            if (!validateCourse(course)) {
                return false;
            }
            course.setStatus(CourseStatus.ACTIVE);
            courseDAO.createCourse(course);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCourse(Course course) {
        try {
            if (!validateCourse(course)) {
                return false;
            }
            courseDAO.updateCourse(course);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Course> getCoursesByInstitution(int institutionId) {
        try {
            return courseDAO.getCoursesByInstitution(institutionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course getCourseById(int courseId) {
        try {
            return courseDAO.getCourseById(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean validateCourse(Course course) {
        return course != null &&
               course.getCourseTitle() != null && !course.getCourseTitle().isEmpty() &&
               course.getCourseCode() != null && !course.getCourseCode().isEmpty() &&
               course.getTerm() != null && !course.getTerm().isEmpty() &&
               course.getSchedule() != null &&
               course.getDeliveryMethod() != null &&
               course.getOutline() != null && !course.getOutline().isEmpty() &&
               course.getCompensation() != null && course.getCompensation().compareTo(BigDecimal.ZERO) > 0 &&
               course.getApplicationDeadline() != null;
    }

    public boolean updateCourseStatus(int courseId, CourseStatus status) {
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) return false;
        
        String oldStatus = course.getStatus().toString();
        course.setStatus(status);
        boolean updated = courseDAO.updateCourse(course);
        
        if (updated) {
            NotificationService.getInstance().notifyCourseStatusChange(
                course, 
                oldStatus, 
                status.toString()
            );
        }
        return updated;
    }

    public boolean canApplyToCourse(AcademicProfessional professional, Course course) {
        if (!professional.isProfileComplete()) {
            return false;
        }
        
        Date now = new Date();
        return course.getStatus() == CourseStatus.ACTIVE && 
               course.getApplicationDeadline().after(now);
    }

    public List<Course> searchCourses(CourseSearchCriteria criteria) {
        // Validate criteria
        if (criteria == null) {
            criteria = new CourseSearchCriteria();
        }

        // Perform search
        return courseDAO.searchCourses(criteria);
    }

    public Set<String> getAvailableTerms() {
        return courseDAO.getAvailableTerms();
    }

    public List<Course> getRecommendedCourses(int professionalId) {
        // Get professional's expertise and qualifications
        AcademicProfessional professional = UserService.getInstance().getProfessionalById(professionalId);
        if (professional == null) {
            return Collections.emptyList();
        }

        // Create search criteria based on professional's profile
        CourseSearchCriteria criteria = new CourseSearchCriteria();
        criteria.setActiveOnly(true);
        criteria.setOpenOnly(true);
        
        // Search using expertise keywords
        String expertiseKeywords = String.join(" ", professional.getExpertise());
        criteria.setKeyword(expertiseKeywords);
        
        return courseDAO.searchCourses(criteria);
    }
}
