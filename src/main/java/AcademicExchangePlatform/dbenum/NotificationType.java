package AcademicExchangePlatform.dbenum;

public enum NotificationType {
    APPLICATION_STATUS,
    NEW_COURSE,
    DEADLINE_REMINDER,
    SYSTEM;

    @Override
    public String toString() {
        return name();
    }
} 