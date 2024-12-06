package model;

public class Course {
    private int courseId;
    private String courseTitle;
    private String courseCode;
    private String term;
    private String schedule;
    private String deliveryMethod;
    private String outline;
    private String preferredQualifications;
    private double compensation;

    // Constructor
    public Course() {}

    public Course(int courseId, String courseTitle, String courseCode, String term, String schedule, String deliveryMethod,
                  String outline, String preferredQualifications, double compensation) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.term = term;
        this.schedule = schedule;
        this.deliveryMethod = deliveryMethod;
        this.outline = outline;
        this.preferredQualifications = preferredQualifications;
        this.compensation = compensation;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getPreferredQualifications() {
        return preferredQualifications;
    }

    public void setPreferredQualifications(String preferredQualifications) {
        this.preferredQualifications = preferredQualifications;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

    // ToString
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", term='" + term + '\'' +
                ", schedule='" + schedule + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", outline='" + outline + '\'' +
                ", preferredQualifications='" + preferredQualifications + '\'' +
                ", compensation=" + compensation +
                '}';
    }
}
