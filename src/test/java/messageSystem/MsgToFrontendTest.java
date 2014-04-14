package messageSystem;

import base.Abonent;
import base.Address;
import base.Frontend;
import org.eclipse.jetty.server.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 4:59 PM

 */
public class MsgToFrontendTest {
    @Test
    public void testExec() throws Exception {
        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy front = new Testy();
        msgSys.addService(front, "front");
        Address add = msgSys.getAddressByName("front");
        msgSys.putMsg(add, new TestMsgToFront(add, add));
        msgSys.execForAbonent(front);
        Assert.assertTrue(front.gotTheMessage);
        Testy1 notFront = new Testy1();
        msgSys.addService(notFront, "notFront");
        add = msgSys.getAddressByName("notFront");
        msgSys.putMsg(add, new TestMsgToFront(add, add));
        msgSys.execForAbonent(notFront);
        Assert.assertFalse(notFront.gotTheMessage);

    }

    private class TestMsgToFront extends MsgToFrontend {
        public TestMsgToFront(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(Frontend frontend) {
            ((Testy)frontend).gotTheMessage = true;
        }
    }

    private class Testy implements Frontend {
        private Address address = new Address();
        private boolean gotTheMessage = false;

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request,
                           HttpServletResponse response) throws IOException, ServletException {

        }

        @Override
        public Address getAddress() {
            return address;
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
