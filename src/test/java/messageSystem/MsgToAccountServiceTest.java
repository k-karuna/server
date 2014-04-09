package messageSystem;

import base.Abonent;
import base.AccountService;
import base.Address;
import base.MessageSystem;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 6:11 PM

 */
public class MsgToAccountServiceTest {
    public class TestyMsg extends MsgToAccountService {
        public TestyMsg(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(AccountService service) {
            ((Testy)service).gotTheMessage = true;
        }
    }

    @Test
    public void testExec() throws Exception {
        MessageSystemImpl msgSys = new MessageSystemImpl();
        Testy service = new Testy();
        msgSys.addService(service, "service");
        Address add = msgSys.getAddressByName("service");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(service);
        Assert.assertTrue(service.gotTheMessage);
        Testy1 notservice = new Testy1();
        msgSys.addService(notservice, "notservice");
        add = msgSys.getAddressByName("notservice");
        msgSys.putMsg(add, new TestyMsg(add, add));
        msgSys.execForAbonent(notservice);
        Assert.assertFalse(notservice.gotTheMessage);
    }

    private class Testy implements AccountService {
        private boolean gotTheMessage = false;
        private Address addres = new Address();

        @Override
        public int getUserId(String nick, String password) {
            return 0;
        }

        @Override
        public MessageSystem getMessageSystem() {
            return null;
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
