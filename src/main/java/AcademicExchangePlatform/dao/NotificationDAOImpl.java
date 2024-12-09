package AcademicExchangePlatform.dao;

import AcademicExchangePlatform.model.Notification;
import AcademicExchangePlatform.model.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {
    private static NotificationDAOImpl instance;
    
    private NotificationDAOImpl() {}
    
    public static NotificationDAOImpl getInstance() {
        if (instance == null) {
            synchronized(NotificationDAOImpl.class) {
                if (instance == null) {
                    instance = new NotificationDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean createNotification(Notification notification) {
        String query = "INSERT INTO notifications (userId, message, dateSent, isRead, type, relatedEntityId) " +
                      "VALUES (?, ?, NOW(), false, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, notification.getUserId());
            stmt.setString(2, notification.getMessage());
            stmt.setString(3, notification.getType());
            stmt.setString(4, notification.getRelatedEntityId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications WHERE userId = ? ORDER BY dateSent DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(extractNotificationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return notifications;
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications WHERE userId = ? AND isRead = false ORDER BY dateSent DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(extractNotificationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return notifications;
    }

    @Override
    public boolean markAsRead(int notificationId) {
        String query = "UPDATE notifications SET isRead = true WHERE notificationId = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, notificationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean markAllAsRead(int userId) {
        String query = "UPDATE notifications SET isRead = true WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteNotification(int notificationId) {
        String query = "DELETE FROM notifications WHERE notificationId = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, notificationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getUnreadCount(int userId) {
        String query = "SELECT COUNT(*) FROM notifications WHERE userId = ? AND isRead = false";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    private Notification extractNotificationFromResultSet(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notificationId"));
        notification.setUserId(rs.getInt("userId"));
        notification.setMessage(rs.getString("message"));
        notification.setDateSent(rs.getTimestamp("dateSent"));
        notification.setRead(rs.getBoolean("isRead"));
        notification.setType(rs.getString("type"));
        notification.setRelatedEntityId(rs.getString("relatedEntityId"));
        return notification;
    }
}
