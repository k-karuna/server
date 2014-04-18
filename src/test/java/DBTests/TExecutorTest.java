package DBTests;

import dbService.TExecutor;
import dbService.TResultHandler;
import dbService.UserDataSet;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;

/**
 * Created by velikolepnii on 10.04.14.
 */
public class TExecutorTest {
    private Connection connection;
    private String name;

    @BeforeMethod
    public void setUp() throws Exception {
        Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
        DriverManager.registerDriver(driver);
        String url="jdbc:mysql://localhost:3306/checkers?user=checkers&password=QSQ9D9BUBW93DK8A7H9FPXOB5OLOP84BA4CJRWK96VN0GPVC6P";
        connection = DriverManager.getConnection(url);
    }
    @Test
    public void testFindPositionOne() throws Exception {
        TExecutor.findPosition(connection, name, new int[]{4, 3, 5, 6, 5, 4}, 3, 2);

    }

    @Test
    public void testFindUser() throws Exception {
        name = new BigInteger(65, new SecureRandom()).toString(16);
        TExecutor.addUser(connection, name, "yecgaa");
        Assert.assertNotNull(TExecutor.findUser(connection, name));
    }

    @Test
    public void testFindUserNull() throws Exception {
        TExecutor.findUser(connection, "$% limit 0,10;<>-- ");

    }
    @Test
    public void testGetUSD() throws Exception {
        TExecutor.getUDS(connection, name, "yecgaa", new TResultHandler<UserDataSet>(){
            @Override
            public UserDataSet handle(ResultSet result){
                try {
                    if(result.first()){
                        int id = result.getInt("id");
                        int rating = result.getInt("rating");
                        int winQuantity = result.getInt("win_quantity");
                        int loseQuantity = result.getInt("lose_quantity");
                        return new UserDataSet(id,name, rating, winQuantity, loseQuantity);
                    }
                }
                catch (SQLException e) {
                    System.err.println("\nError");
                    System.err.println("DBServiceImpl, addUDS");
                    System.err.println(e.getMessage());
                }
                return null;
            }
        });

    }


    @AfterMethod
    public void teardown() throws Exception {
        connection.close();
    }
}
