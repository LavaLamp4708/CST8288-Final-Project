package AcademicExchangePlatform.model;

public class EmailNotificationDecorator extends NotificationDecorator {
    public EmailNotificationDecorator(Subject wrappedSubject) {
        super(wrappedSubject);
    }

    @Override
    public void notifyObservers(String message, int userId, String type, String entityId) {
        super.notifyObservers(message, userId, type, entityId);
        sendEmail(message);
    }

    private void sendEmail(String message) {
        System.out.println("Email sent: " + message);
    }
}