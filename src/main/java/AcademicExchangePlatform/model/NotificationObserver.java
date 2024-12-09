package AcademicExchangePlatform.model;

public interface NotificationObserver {
    void update(String message, int userId, String type, String entityId);
}
