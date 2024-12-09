package service;

import dao.CourseDAO;
import dao.CourseDAOImpl;
import model.Course;

import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;

    public CourseService() {
        // Initialize the DAO implementation
        this.courseDAO = new CourseDAOImpl();
    }

    /**
     * Add a new course to the system.
     *
     * @param course The course object to add.
     * @return true if the course is successfully added, false otherwise.
     */
    public boolean createCourse(Course course) {
        if (validateCourse(course)) {
            return courseDAO.addCourse(course);
        }
        return false;
    }

    /**
     * Retrieve a course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return The course object if found, null otherwise.
     */
    public Course getCourseById(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    /**
     * Retrieve all courses from the system.
     *
     * @return A list of all courses.
     */
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    /**
     * Update the details of an existing course.
     *
     * @param course The course object with updated details.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateCourse(Course course) {
        if (course.getCourseId() > 0 && validateCourse(course)) {
            return courseDAO.updateCourse(course);
        }
        return false;
    }

    /**
     * Delete a course by its ID.
     *
     * @param courseId The ID of the course to delete.
     * @return true if the deletion is successful, false otherwise.
     */
    public boolean deleteCourse(int courseId) {
        return courseDAO.deleteCourse(courseId);
    }

    /**
     * Validate the course object to ensure all required fields are present.
     *
     * @param course The course object to validate.
     * @return true if the course is valid, false otherwise.
     */
    private boolean validateCourse(Course course) {
        return course.getCourseTitle() != null && !course.getCourseTitle().isEmpty()
                && course.getCourseCode() != null && !course.getCourseCode().isEmpty()
                && course.getTerm() != null && !course.getTerm().isEmpty()
                && course.getSchedule() != null && !course.getSchedule().isEmpty()
                && course.getDeliveryMethod() != null && !course.getDeliveryMethod().isEmpty()
                && course.getCompensation() > 0;
    }
}
