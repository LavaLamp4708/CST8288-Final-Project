package model;

public class CourseBuilder {
    private int courseId;
    private String courseTitle;
    private String courseCode;
    private String term;
    private String schedule;
    private String deliveryMethod;
    private String outline;
    private String preferredQualifications;
    private double compensation;

    // Setters
    public CourseBuilder setCourseId(int courseId) {
        this.courseId = courseId;
        return this;
    }

    public CourseBuilder setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
        return this;
    }

    public CourseBuilder setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public CourseBuilder setTerm(String term) {
        this.term = term;
        return this;
    }

    public CourseBuilder setSchedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    public CourseBuilder setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public CourseBuilder setOutline(String outline) {
        this.outline = outline;
        return this;
    }

    public CourseBuilder setPreferredQualifications(String preferredQualifications) {
        this.preferredQualifications = preferredQualifications;
        return this;
    }

    public CourseBuilder setCompensation(double compensation) {
        this.compensation = compensation;
        return this;
    }

    // Build Method
    public Course build() {
        return new Course(courseId, courseTitle, courseCode, term, schedule, deliveryMethod, outline, preferredQualifications, compensation);
    }
}
