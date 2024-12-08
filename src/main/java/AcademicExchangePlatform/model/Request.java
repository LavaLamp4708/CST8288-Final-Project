package AcademicExchangePlatform.model;
import java.util.Date;

/**
 * Request model class outlines the makeup of a request.
 */

public class Request {
    private int requestId;
    private int courseId;
    private int professionalId;
    private String status;
    private Date requestDate;
    private Date decisionDate;

    //courses table attributes
    private String courseTitle;
    private String courseCode;
    private String term;
    private String schedule;
    private String deliveryMethod;

    //academic institution name
    private String academicInstitutionName;

    public Request(
        int requestId, 
        int courseId, 
        int professionalId, 
        String status, 
        Date requestDate,
        Date decisionDate
    ) {
        this.requestId = requestId;
        this.courseId = courseId;
        this.professionalId = professionalId;
        this.status = status;
        this.requestDate = requestDate;
        this.decisionDate = decisionDate;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getProfessionalId() {
        return this.professionalId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getDecisionDate() {
        return this.decisionDate;
    }

    public void setDecisionDate(Date decisionDate) {
        this.decisionDate = decisionDate;
    }

    public boolean submitRequest(int courseId, int professionalId){
        return false;
    }

    public boolean updateStatus(int requestId, String status){
        return false;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getAcademicInstitutionName() {
        return this.academicInstitutionName;
    }

    public void setAcademicInstitutionName(String academicInstitutionName) {
        this.academicInstitutionName = academicInstitutionName;
    }
}
