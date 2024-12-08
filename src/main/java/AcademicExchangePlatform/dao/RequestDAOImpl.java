package AcademicExchangePlatform.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import AcademicExchangePlatform.model.DatabaseConnection;
import AcademicExchangePlatform.model.Request;

/**
 * Implementation of the {@link RequestDAO} interface for managing course application requests
 * in the Academic Exchange Platform.
 * 
 * <p>This class uses a singleton design pattern to ensure a single instance of the DAO is
 * created. It provides methods to add, retrieve, update, and delete requests stored in the
 * database.</p>
 * 
 * <p><b>Note:</b> This class interacts directly with the database and depends on the
 * {@link DatabaseConnection} class to manage the connection.</p>
 * 
 * <p><b>Author:</b> Peter Stainforth</p>
 * 
 * @see RequestDAO
 * @see AcademicExchangePlatform.model.DatabaseConnection
 */
public class RequestDAOImpl implements RequestDAO{

    private Connection connection;
    private static volatile RequestDAOImpl dao;

    private RequestDAOImpl() 
    throws 
        ClassNotFoundException, 
        SQLException
    {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Returns the singleton instance of {@code RequestDAOImpl}.
     * 
     * @return the singleton instance of {@link RequestDAO}.
     * @throws ClassNotFoundException if the database driver class is not found.
     * @throws SQLException if a database access error occurs.
     */
    public static RequestDAO getInstance() 
    throws 
        ClassNotFoundException, 
        SQLException
    {
        if(dao == null) {
            synchronized (RequestDAOImpl.class){
                if(dao == null){
                    dao = new RequestDAOImpl();
                }
            }
        }
        return dao;
    }

    /**
     * Adds a new request to the database.
     * 
     * @param request the {@link Request} object containing the request details.
     * @return {@code true} if the request was successfully added, {@code false} otherwise.
     */
    @Override
    public boolean addRequest(Request request) {

        SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String requestDate = mysqlDateFormat.format(request.getRequestDate());
        String decisionDate = mysqlDateFormat.format(request.getDecisionDate());

        String query = "INSERT INTO courseapplications(courseId, professionalId, statis, requestDate, decisionDate)"
            + "VALUES(?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, request.getCourseId());
            preparedStatement.setInt(2, request.getProfessionalId());
            preparedStatement.setString(3, request.getStatus());
            preparedStatement.setString(4, requestDate);
            preparedStatement.setString(5, decisionDate);
            int insertResult = preparedStatement.executeUpdate();
            if(insertResult < 0 ){
                throw new SQLException(String.format("Unable to insert:\n%s", request));
            }
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
        
    }

    /**
     * Retrieves a request from the database by its ID.
     * 
     * @param requestId the ID of the request to retrieve.
     * @return the {@link Request} object if found, or {@code null} if not found.
     */
    @Override
    public Request getRequestById(int requestId) {
        Request request = null;

        String query = "SELECT * FROM courseapplications WHERE applicationId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Timestamp requestDateTS = resultSet.getTimestamp("applicationDate");
                Timestamp decisionDateTS = resultSet.getTimestamp("decisionDate");
                Date requestDate = (requestDateTS == null) ? null: new Date(requestDateTS.getTime());
                Date decisionDate = (decisionDateTS == null) ? null : new Date(decisionDateTS.getTime());

                request = new Request(
                    resultSet.getInt("requestId"),
                    resultSet.getInt("courseId"),
                    resultSet.getInt("professionalId"),
                    resultSet.getString("status"),
                    requestDate,
                    decisionDate
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return request;
    }

    /**
     * Cancels a request by deleting it from the database.
     * 
     * @param requestId the ID of the request to cancel.
     */
    @Override
    public void cancelRequestById(int requestId){
        String query = "DELETE FROM courseapplications WHERE applicationId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, requestId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all requests associated with a specific course.
     * 
     * @param courseId the ID of the course.
     * @return a {@link List} of {@link Request} objects for the specified course.
     */
    @Override
    public List<Request> getRequestByCourse(int courseId) {
        List<Request> list = new ArrayList<Request>();

        String query = "SELECT * FROM courseapplications WHERE courseId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Timestamp requestDateTS = resultSet.getTimestamp("applicationDate");
                Timestamp decisionDateTS = resultSet.getTimestamp("decisionDate");
                Date requestDate = (requestDateTS == null) ? null: new Date(requestDateTS.getTime());
                Date decisionDate = (decisionDateTS == null) ? null : new Date(decisionDateTS.getTime());

                list.add(
                    new Request(
                        resultSet.getInt("applicationId"),
                        resultSet.getInt("courseId"),
                        resultSet.getInt("professionalId"),
                        resultSet.getString("status"),
                        requestDate,
                        decisionDate
                    )
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves all requests submitted by a specific user.
     * 
     * @param userId the ID of the user.
     * @return a {@link List} of {@link Request} objects submitted by the user.
     */
    @Override
    public List<Request> getRequestByUserId(int userId){
        List<Request> list = new ArrayList<Request>();

        String query = "SELECT * FROM courseapplications AS ca JOIN courses AS c ON ca.courseId = c.courseId JOIN academicinstitutions AS ai ON c.institutionId = ai.userId WHERE ca.professionalId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Timestamp requestDateTS = resultSet.getTimestamp("ca.applicationDate");
                Timestamp decisionDateTS = resultSet.getTimestamp("ca.decisionDate");
                Date requestDate = (requestDateTS == null) ? null: new Date(requestDateTS.getTime());
                Date decisionDate = (decisionDateTS == null) ? null : new Date(decisionDateTS.getTime());
                Request request = new Request(
                        resultSet.getInt("ca.applicationId"),
                        resultSet.getInt("ca.courseId"),
                        resultSet.getInt("ca.professionalId"),
                        resultSet.getString("ca.status"),
                        requestDate,
                        decisionDate
                    );
                request.setCourseTitle(resultSet.getString("c.courseTitle"));
                request.setCourseCode(resultSet.getString("c.courseCode"));
                request.setTerm(resultSet.getString("c.term"));
                request.setSchedule(resultSet.getString("c.schedule"));
                request.setDeliveryMethod(resultSet.getString("c.deliveryMethod"));
                request.setAcademicInstitutionName(resultSet.getString("ai.institutionName"));
                list.add(request);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates the status of a specific request.
     * 
     * @param requestId the ID of the request to update.
     * @param status the new status to set for the request (e.g., "PENDING", "ACCEPTED", "REJECTED").
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    @Override
    public boolean updateRequestStatus(int requestId, String status) {
        String query = "UPDATE courseapplications SET status = ? WHERE applicationId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, requestId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
