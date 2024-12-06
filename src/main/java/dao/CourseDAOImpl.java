package dao;

import model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    private Connection connection;

    public CourseDAOImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEP", "root", "Hitesh1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO Courses (courseTitle, courseCode, term, schedule, deliveryMethod, outline, preferredQualifications, compensation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, course.getCourseTitle());
            ps.setString(2, course.getCourseCode());
            ps.setString(3, course.getTerm());
            ps.setString(4, course.getSchedule());
            ps.setString(5, course.getDeliveryMethod());
            ps.setString(6, course.getOutline());
            ps.setString(7, course.getPreferredQualifications());
            ps.setDouble(8, course.getCompensation());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM Courses WHERE courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("courseId"),
                    rs.getString("courseTitle"),
                    rs.getString("courseCode"),
                    rs.getString("term"),
                    rs.getString("schedule"),
                    rs.getString("deliveryMethod"),
                    rs.getString("outline"),
                    rs.getString("preferredQualifications"),
                    rs.getDouble("compensation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                courses.add(new Course(
                    rs.getInt("courseId"),
                    rs.getString("courseTitle"),
                    rs.getString("courseCode"),
                    rs.getString("term"),
                    rs.getString("schedule"),
                    rs.getString("deliveryMethod"),
                    rs.getString("outline"),
                    rs.getString("preferredQualifications"),
                    rs.getDouble("compensation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public boolean updateCourse(Course course) {
        String sql = "UPDATE Courses SET courseTitle = ?, courseCode = ?, term = ?, schedule = ?, deliveryMethod = ?, outline = ?, preferredQualifications = ?, compensation = ? WHERE courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, course.getCourseTitle());
            ps.setString(2, course.getCourseCode());
            ps.setString(3, course.getTerm());
            ps.setString(4, course.getSchedule());
            ps.setString(5, course.getDeliveryMethod());
            ps.setString(6, course.getOutline());
            ps.setString(7, course.getPreferredQualifications());
            ps.setDouble(8, course.getCompensation());
            ps.setInt(9, course.getCourseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM Courses WHERE courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
