package messageSystem;

import base.Abonent;
import base.Address;
import base.WebSocket;
import gameClasses.Snapshot;
import gameClasses.Stroke;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * User: lena
 * Date: 4/9/14
 * Time: 6:06 PM
 */
public class MsgToWebSocketTest {
    public class TestyMsg extends MsgToWebSocket {
        public TestyMsg(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(WebSocket webSocket) {
            ((Testy)webSocket).gotTheMessage = true;
        }
    }

    @Test
    public void testExec() throws Exception {
        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy socket = new Testy();
        msgSys.addService(socket, "socket");
        Address add = msgSys.getAddressByName("socket");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(socket);
        Assert.assertTrue(socket.gotTheMessage);
        Testy1 notsocket = new Testy1();
        msgSys.addService(notsocket, "notsocket");
        add = msgSys.getAddressByName("notsocket");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(notsocket);
        Assert.assertFalse(notsocket.gotTheMessage);

    }
    


    private class Testy implements WebSocket {
        private boolean gotTheMessage = false;
        private Address address = new Address();

        @Override
        public void sendStroke(Map<Integer, Stroke> userIdToStroke) {

        }

        @Override
        public void doneSnapshot(int id, Snapshot snapshot) {

        }

        @Override
        public void updateUsersColor(Map<String, String> usersToColors) {

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
