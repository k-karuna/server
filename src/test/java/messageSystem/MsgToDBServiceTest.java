package messageSystem;

import base.*;
import dbService.UserDataSet;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * User: lena
 * Date: 4/9/14
 * Time: 6:14 PM
 */
public class MsgToDBServiceTest {
    
    
    @Test
    public void testExec() throws Exception {
        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy DB = new Testy();
        msgSys.addService(DB, "DB");
        Address add = msgSys.getAddressByName("DB");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(DB);
        Assert.assertTrue(DB.gotTheMessage);
        Testy1 notDB = new Testy1();
        msgSys.addService(notDB, "notDB");
        add = msgSys.getAddressByName("notDB");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(notDB);
        Assert.assertFalse(notDB.gotTheMessage);

    }

    public class TestyMsg extends MsgToDBService {
        public TestyMsg(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(DataAccessObject service) {
            ((Testy)service).gotTheMessage = true;
        }
    }

    private class Testy implements DataAccessObject {
        private boolean gotTheMessage = false;
        private Address addres = new Address();

        @Override
        public MessageSystem getMessageSystem() {
            return null;  
        }

        @Override
        public UserDataSet getUDS(String login, String password) {
            return null; 
        }

        @Override
        public boolean addUDS(String login, String password) {
            return false;  
        }

        @Override
        public void updateUsers(List<UserDataSet> users) {
           
        }

        @Override
        public Address getAddress() {
            return addres;
        }

        @Override
        public void run() {
            
        }
    }
    private class Testy1 implements Abonent {
        public boolean gotTheMessage = false;
        public Address address = new Address();

        @Override
        public Address getAddress() {
            return address;
        }
    }
}
