package AcademicExchangePlatform.dao;
import org.junit.Test;

import java.sql.SQLException;

import org.junit.Before;    
    
public class RequestDAOImplTest {

    @Before
    public void setup() throws ClassNotFoundException, SQLException{
        RequestDAO dao = RequestDAOImpl.getInstance();
    }
        
    @Test
    public void test() {
        
    }
}
    