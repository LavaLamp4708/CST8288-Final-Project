package AcademicExchangePlatform.dao;
import AcademicExchangePlatform.dbenum.CourseStatus;
import AcademicExchangePlatform.model.Course;
import AcademicExchangePlatform.model.CourseSearchCriteria;
import java.util.List;
import java.util.Set;

public interface CourseDAO {
    boolean createCourse(Course course);
    boolean updateCourse(Course course);
    boolean deleteCourse(int courseId);
    Course getCourseById(int courseId);
    List<Course> getAllCourses();
    List<Course> getCoursesByInstitution(int institutionId);
    boolean updateCourseStatus(int courseId, CourseStatus status);
    List<Course> searchCourses(CourseSearchCriteria criteria);
    Set<String> getAvailableTerms();
}
