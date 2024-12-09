package AcademicExchangePlatform.dao;

import AcademicExchangePlatform.model.CourseApplication;
import AcademicExchangePlatform.model.DatabaseConnection;
import AcademicExchangePlatform.dbenum.ApplicationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseApplicationDAOImpl implements CourseApplicationDAO {

    @Override
    public boolean createApplication(CourseApplication application) {
        String query = "INSERT INTO course_applications (courseId, professionalId, coverLetter, " +
                      "additionalDocuments, applicationDate, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, application.getCourseId());
            stmt.setInt(2, application.getProfessionalId());
            stmt.setString(3, application.getCoverLetter());
            stmt.setString(4, application.getAdditionalDocuments());
            stmt.setTimestamp(5, new Timestamp(application.getApplicationDate().getTime()));
            stmt.setString(6, application.getStatus().toString());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateApplicationStatus(int applicationId, ApplicationStatus status) {
        String query = "UPDATE course_applications SET status = ? WHERE applicationId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status.toString());
            stmt.setInt(2, applicationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CourseApplication getApplicationById(int applicationId) {
        String query = "SELECT * FROM course_applications WHERE applicationId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractApplicationFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CourseApplication> getApplicationsByCourse(int courseId) {
        List<CourseApplication> applications = new ArrayList<>();
        String query = "SELECT * FROM course_applications WHERE courseId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    applications.add(extractApplicationFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    @Override
    public List<CourseApplication> getApplicationsByProfessional(int professionalId) {
        List<CourseApplication> applications = new ArrayList<>();
        String query = "SELECT * FROM course_applications WHERE professionalId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, professionalId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    applications.add(extractApplicationFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    @Override
    public boolean withdrawApplication(int applicationId) {
        return updateApplicationStatus(applicationId, ApplicationStatus.WITHDRAWN);
    }

    private CourseApplication extractApplicationFromResultSet(ResultSet rs) throws SQLException {
        CourseApplication application = new CourseApplication();
        application.setApplicationId(rs.getInt("applicationId"));
        application.setCourseId(rs.getInt("courseId"));
        application.setProfessionalId(rs.getInt("professionalId"));
        application.setCoverLetter(rs.getString("coverLetter"));
        application.setAdditionalDocuments(rs.getString("additionalDocuments"));
        application.setApplicationDate(rs.getTimestamp("applicationDate"));
        application.setStatus(ApplicationStatus.valueOf(rs.getString("status")));
        return application;
    }
} 