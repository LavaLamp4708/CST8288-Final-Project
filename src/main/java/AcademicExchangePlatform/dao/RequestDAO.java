package AcademicExchangePlatform.dao;
import java.util.List;
import AcademicExchangePlatform.model.Request;

public interface RequestDAO {
    public boolean addRequest(Request request);

    public Request getRequestById(int requestId);

    public void cancelRequestById(int requestId);

    public List<Request> getRequestByCourse(int courseId);

    public List<Request> getRequestByUserId(int userId);

    public boolean updateRequestStatus(int requestId, String status);
}
