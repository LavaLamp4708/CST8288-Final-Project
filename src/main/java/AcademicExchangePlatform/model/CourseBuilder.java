package AcademicExchangePlatform.model;

import AcademicExchangePlatform.dbenum.DeliveryMethod;
import AcademicExchangePlatform.dbenum.Schedule;

import java.math.BigDecimal;

public class CourseBuilder {
    private int courseId;
    private String courseTitle;
    private String courseCode;
    private String term;
    private Schedule schedule;
    private DeliveryMethod deliveryMethod;
    private String outline;
    private String preferredQualifications;
    private BigDecimal compensation;
    private int institutionId;

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

    public CourseBuilder setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public CourseBuilder setDeliveryMethod(DeliveryMethod deliveryMethod) {
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

    public CourseBuilder setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
        return this;
    }

    public CourseBuilder setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
        return this;
    }

    public Course build() {
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseTitle(courseTitle);
        course.setCourseCode(courseCode);
        course.setTerm(term);
        course.setSchedule(schedule);
        course.setDeliveryMethod(deliveryMethod);
        course.setOutline(outline);
        course.setPreferredQualifications(preferredQualifications);
        course.setCompensation(compensation);
        course.setInstitutionId(institutionId);
        return course;
    }
}
