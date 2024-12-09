package AcademicExchangePlatform.dao;

import java.util.List;
import AcademicExchangePlatform.model.Notification;

public interface NotificationDAO {
    boolean createNotification(Notification notification);
    List<Notification> getNotificationsByUserId(int userId);
    List<Notification> getUnreadNotificationsByUserId(int userId);
    boolean markAsRead(int notificationId);
    boolean markAllAsRead(int userId);
    boolean deleteNotification(int notificationId);
    int getUnreadCount(int userId);
}