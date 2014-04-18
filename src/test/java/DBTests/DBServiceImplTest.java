package DBTests;

import base.MessageSystem;
import base.UserData;
import chat.GameChatImpl;
import dbService.DBServiceImpl;
import dbService.UserDataSet;
import messageSystem.MessageSystemImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by velikolepnii on 08.04.14.
 */
public class DBServiceImplTest {

    private MessageSystem messageSystem;
    String url="jdbc:mysql://localhost:3306/checkers?user=checkers&password=QSQ9D9BUBW93DK8A7H9FPXOB5OLOP84BA4CJRWK96VN0GPVC6P";

    @BeforeMethod
    public void setUp() {
        messageSystem = new MessageSystemImpl();

    }
    @Test
    public void testaddUDS() {
        try{

            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
        }
        catch(Exception e){
            System.err.println("\nError");
            System.err.println("DVServiceImpl, run1");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        try{
            Connection connection = DriverManager.getConnection(url);
            DBServiceImpl dbService = new DBServiceImpl(messageSystem, connection);
            boolean isAdded = dbService.addUDS(new BigInteger(130, new SecureRandom()).toString(32).substring(0, 9), "yecgaa");
            Assert.assertTrue(isAdded);
        }
        catch(Exception e){
            System.err.println("\nError");
            System.err.println("DVServiceImpl, run2");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
    @Test
    public void testgetUDS() {
        try{

            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
        }
        catch(Exception e){
            System.err.println("\nError");
            System.err.println("DVServiceImpl, run1");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        try{
            Connection connection = DriverManager.getConnection(url);
            DBServiceImpl dbService = new DBServiceImpl(messageSystem, connection);
            dbService.addUDS("Karuna", "yecgaa");
            UserDataSet userDataSet = dbService.getUDS("Karuna", "yecgaa");
            Assert.assertNotNull(userDataSet);
        }
        catch(Exception e){
            System.err.println("\nError");
            System.err.println("DVServiceImpl, run2");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    @Test
    public void testUpdateUsers() {
        String nick1 = new BigInteger(130, new SecureRandom()).toString(32).substring(0, 9);
        String nick2 = new BigInteger(130, new SecureRandom()).toString(32).substring(0, 9);
        List<UserDataSet> users = new ArrayList<UserDataSet>();

        try{

            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
        }
        catch(Exception e){
            System.err.println("\nError");
            System.err.println("DVServiceImpl, run1");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        try{
            Connection connection = DriverManager.getConnection(url);
            DBServiceImpl dbService = new DBServiceImpl(messageSystem, connection);
            dbService.addUDS(nick1, "yecgaa");
            dbService.addUDS(nick2, "yecgaa");
            UserDataSet user = dbService.getUDS(nick1, "yecgaa");
            users.add(new UserDataSet(user.getId(), nick1, 5, 4, 3));
            user = dbService.getUDS(nick1, "yecgaa");
            users.add(new UserDataSet(user.getId(), nick2, 3, 4, 5));
            dbService.updateUsers(users);
            user = dbService.getUDS(nick1, "yecgaa");
            boolean flag = false;
            if (users.get(0).getNick() == user.getNick() && users.get(0).getRating() == user.getRating() &&
                    users.get(0).getWinQuantity() == user.getWinQuantity() &&
                    users.get(0).getLoseQuantity() == user.getLoseQuantity()) flag = true;
            Assert.assertTrue(flag);
        }
        catch(Exception e){
            System.err.println("\nError");
            System.err.println("DVServiceImpl, run2");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
