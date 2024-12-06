package AcademicExchangePlatform.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import AcademicExchangePlatform.model.Request;

/**
 * Implementation of the RequestDAO interface.
 * @author Peter Stainforth
 */

public class RequestDAOImpl implements RequestDAO{

    private Connection connection;
    private static volatile RequestDAOImpl dao;

    private RequestDAOImpl() 
    throws 
        ClassNotFoundException, 
        SQLException
    {
        Class.forName(
            "com.mysql.cj.jdbc.Driver"
        );
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/AcademicExchangePlatform", 
            "AcademicExchangePlatform", 
            "password"
        );
    }

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

    @Override
    public boolean addRequest(Request request) {

        SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String requestDate = mysqlDateFormat.format(request.getRequestDate());
        String decisionDate = mysqlDateFormat.format(request.getDecisionDate());

        String query = "INSERT INTO Requests(courseId, professionalId, statis, requestDate, decisionDate)"
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

    @Override
    public Request getRequestById(int requestId) {
        Request request = null;

        String query = "SELECT * FROM Requests WHERE requestId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Timestamp requestDateTS = resultSet.getTimestamp("requestDate");
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

    @Override
    public void cancelRequestById(int requestId){
        String query = "DELETE FROM Requests WHERE requestId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, requestId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Request> getRequestByCourse(int courseId) {
        List<Request> list = new ArrayList<Request>();

        String query = "SELECT * FROM Requests WHERE courseId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Timestamp requestDateTS = resultSet.getTimestamp("requestDate");
                Timestamp decisionDateTS = resultSet.getTimestamp("decisionDate");
                Date requestDate = (requestDateTS == null) ? null: new Date(requestDateTS.getTime());
                Date decisionDate = (decisionDateTS == null) ? null : new Date(decisionDateTS.getTime());

                list.add(
                    new Request(
                        resultSet.getInt("requestId"),
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

    @Override
    public List<Request> getRequestByUserId(int userId){
        List<Request> list = new ArrayList<Request>();

        String query = "SELECT * FROM Requests JOIN Courses ON Requests.courseId = Courses.courseId WHERE userId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Timestamp requestDateTS = resultSet.getTimestamp("requestDate");
                Timestamp decisionDateTS = resultSet.getTimestamp("decisionDate");
                Date requestDate = (requestDateTS == null) ? null: new Date(requestDateTS.getTime());
                Date decisionDate = (decisionDateTS == null) ? null : new Date(decisionDateTS.getTime());
                Request request = new Request(
                        resultSet.getInt("requestId"),
                        resultSet.getInt("courseId"),
                        resultSet.getInt("professionalId"),
                        resultSet.getString("status"),
                        requestDate,
                        decisionDate
                    );
                request.setCourseTitle(resultSet.getString("courseTitle"));
                list.add(request);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateRequestStatus(int requestId, String status) {
        String query = "UPDATE Requests SET status = ? WHERE requestId = ?";

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
