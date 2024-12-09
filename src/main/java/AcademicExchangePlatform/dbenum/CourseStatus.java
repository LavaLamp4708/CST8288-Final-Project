package AcademicExchangePlatform.dbenum;

public enum CourseStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String value;

    CourseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 