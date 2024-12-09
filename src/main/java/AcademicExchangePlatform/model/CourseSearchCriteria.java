package AcademicExchangePlatform.model;

import AcademicExchangePlatform.dbenum.DeliveryMethod;
import AcademicExchangePlatform.dbenum.Schedule;

public class CourseSearchCriteria {
    private String keyword;
    private Schedule schedule;
    private DeliveryMethod deliveryMethod;
    private String term;
    private boolean activeOnly;
    private boolean openOnly;

    // Getters and Setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public boolean isActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(boolean activeOnly) {
        this.activeOnly = activeOnly;
    }

    public boolean isOpenOnly() {
        return openOnly;
    }

    public void setOpenOnly(boolean openOnly) {
        this.openOnly = openOnly;
    }
} 