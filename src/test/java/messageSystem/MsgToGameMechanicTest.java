package messageSystem;

import base.Abonent;
import base.Address;
import base.GameMechanic;
import base.MessageSystem;
import dbService.UserDataSet;
import gameClasses.Snapshot;
import gameClasses.Stroke;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 5:05 PM

 */
public class MsgToGameMechanicTest {
    @Test
    public void testExec() throws Exception {

        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy GM = new Testy();
        msgSys.addService(GM, "GM");
        Address add = msgSys.getAddressByName("GM");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(GM);
        Assert.assertTrue(GM.gotTheMessage);
        Testy1 notGM = new Testy1();
        msgSys.addService(notGM, "notGM");
        add = msgSys.getAddressByName("notGM");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(notGM);
        Assert.assertFalse(notGM.gotTheMessage);

    }
    
    
    private class TestyMsg extends MsgToGameMechanic {
        public TestyMsg(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(GameMechanic gameMechanic) {
            ((Testy)gameMechanic).gotTheMessage = true;
        }
    }

    private class Testy implements GameMechanic {
        private boolean gotTheMessage = false;
        private Address address = new Address();
        

        @Override
        public Map<String, String> createGames(Map<String, UserDataSet> users) {
            return null;  
        }

        @Override
        public Map<Integer, Stroke> checkStroke(int id, Stroke stroke) {
            return null; 
        }

        @Override
        public void removeUser(String sessionId) {
            
        }

        @Override
        public Snapshot getSnapshot(int id) {
            return null; 
        }

        @Override
        public MessageSystem getMessageSystem() {
            return null; 
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
