package AcademicExchangePlatform.model;

import java.util.Date;

public class Notification {
    private int notificationId;
    private int userId;
    private String message;
    private Date dateSent;
    private boolean isRead;
    private String type;
    private String relatedEntityId; // Could be courseId, applicationId, etc.

    // Constructors
    public Notification() {}

    public Notification(int userId, String message, String type, String relatedEntityId) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.relatedEntityId = relatedEntityId;
        this.dateSent = new Date();
        this.isRead = false;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(String relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }
}
