package messageSystem;

import base.Abonent;
import base.Address;
import base.UserData;
import dbService.UserDataSet;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 5:09 PM

 */

public class MsgToUserDataTest {
    
    public class TestyMsg extends MsgToUserData {
        public TestyMsg(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(UserData userData) {
            ((Testy)userData).gotTheMessage = true;
        }
    }
    @Test
    public void testExec() throws Exception {
        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy usrData = new Testy();
        msgSys.addService(usrData, "usrData");
        Address add = msgSys.getAddressByName("usrData");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(usrData);
        Assert.assertTrue(usrData.gotTheMessage);
        Testy1 notusrData = new Testy1();
        msgSys.addService(notusrData, "notusrData");
        add = msgSys.getAddressByName("notusrData");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(notusrData);
        Assert.assertFalse(notusrData.gotTheMessage);

    }
    
    private class Testy implements UserData {
        private boolean gotTheMessage = false;
        private Address address = new Address();

        @Override
        public void updateUserId(String sessionId, UserDataSet user) {
           
        }

        @Override
        public void partyEnd(int winId, int loseId) {

        }

        @Override
        public Address getAddress() {
            return address;
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
