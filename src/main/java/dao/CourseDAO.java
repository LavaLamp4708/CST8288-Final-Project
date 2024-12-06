package dao;

import model.Course;
import java.util.List;

public interface CourseDAO {
    boolean addCourse(Course course); // Adds a new course to the database
    Course getCourseById(int courseId); // Fetches a course by its ID
    List<Course> getAllCourses(); // Retrieves all courses from the database
    boolean updateCourse(Course course); // Updates an existing course
    boolean deleteCourse(int courseId); // Deletes a course by its ID
}
