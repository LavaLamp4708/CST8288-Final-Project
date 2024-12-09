package AcademicExchangePlatform.model;

public interface Subject {
    void registerObserver(NotificationObserver observer);
    void removeObserver(NotificationObserver observer);
    void notifyObservers(String message, int userId, String type, String entityId);
}
