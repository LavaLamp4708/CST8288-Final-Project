package AcademicExchangePlatform.service;
import AcademicExchangePlatform.dao.*;
import AcademicExchangePlatform.model.*;

import java.sql.SQLException;
import java.util.List;

public class RequestService {
    private static RequestService instance;
    private final RequestDAO requestDAO;

    private RequestService() throws ClassNotFoundException, SQLException {
        this.requestDAO = RequestDAOImpl.getInstance();
    }

    public static RequestService getInstance() {
        if (instance == null) {
            try {
                instance = new RequestService();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public boolean submitRequest(Request request) throws ClassNotFoundException, SQLException{
        return requestDAO.addRequest(request);
    }

    public List<Request> getRequestByCourse(int courseId) throws ClassNotFoundException, SQLException{
        return requestDAO.getRequestByCourse(courseId);
    }

    public List<Request> getRequestByUserId(int userId) throws ClassNotFoundException, SQLException{
        return requestDAO.getRequestByUserId(userId);
    }

    public boolean handleRequest(int requestId, String status) throws ClassNotFoundException, SQLException{
        return requestDAO.updateRequestStatus(requestId, status);
    }

    public void cancelRequestById(int requestId) throws ClassNotFoundException, SQLException{
        requestDAO.cancelRequestById(requestId);
    }

    public boolean processRequest(Request request, String decision) {
        boolean updated = requestDAO.updateRequestStatus(request.getRequestId(), decision);
        if (updated) {
            NotificationService.getInstance().notifyRequestUpdate(
                request.getProfessionalId(),
                "Your request has been " + decision,
                "REQUEST_UPDATE",
                String.valueOf(request.getRequestId())
            );
        }
        return updated;
    }
}
