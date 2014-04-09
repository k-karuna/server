package messageSystem;

import base.Abonent;
import base.Address;
import base.GameChat;
import base.MessageSystem;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: lena
 * Date: 4/9/14
 * Time: 4:39 PM
 */
public class MsgToGameChatTest {

    public class TestyMsg extends MsgToGameChat {
        public TestyMsg(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(GameChat gameChat) {
            ((Testy)gameChat).gotTheMessage = true;
        }
    }

    @Test
    public void testExec() throws Exception {
        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy chat = new Testy();
        msgSys.addService(chat, "chat");
        Address add = msgSys.getAddressByName("chat");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(chat);
        Assert.assertTrue(chat.gotTheMessage);
        Testy1 notChat = new Testy1();
        msgSys.addService(notChat, "notchat");
        add = msgSys.getAddressByName("notchat");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(notChat);
        Assert.assertFalse(notChat.gotTheMessage);

    }

    private class Testy implements GameChat {
        public boolean gotTheMessage = false;

        @Override
        public MessageSystem getMessageSystem() {
            return null;
        }

        @Override
        public void createChat(String sessionId1, String sessionId2) {

        }

        @Override
        public Address getAddress() {
            return null;
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
