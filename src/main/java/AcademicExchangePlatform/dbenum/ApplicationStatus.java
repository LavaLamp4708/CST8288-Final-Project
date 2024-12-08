package AcademicExchangePlatform.dbenum;

public enum ApplicationStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    WITHDRAWN;

    @Override
    public String toString() {
        return name();
    }
} 