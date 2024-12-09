package AcademicExchangePlatform.model;

import AcademicExchangePlatform.model.Notification;
import AcademicExchangePlatform.service.NotificationService;

public class NotificationObserverImpl implements NotificationObserver {
    private final NotificationService notificationService;

    public NotificationObserverImpl() {
        this.notificationService = NotificationService.getInstance();
    }

    @Override
    public void update(String message, int userId, String type, String entityId) {
        Notification notification = new Notification(userId, message, type, entityId);
        notificationService.createNotification(notification);
    }
}
