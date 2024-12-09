package AcademicExchangePlatform.dbenum;

public enum NotificationStatus {
    Unread,
    Read;

    @Override
    public String toString() {
        return name(); // Return the exact enum name for database compatibility
    }
}
