package messageSystem;

import base.Abonent;
import base.Address;
import base.Msg;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 4:04 PM

 */
public class MessageSystemImplTest {
    private class Testy implements Abonent {
        public boolean gotTheMessage = false;
        public Address address = new Address();

        @Override
        public Address getAddress() {
            return address;
        }
    }
    public class MsgToTesty extends Msg {
        public MsgToTesty(Address from, Address to) {
            super(from, to);
        }

        @Override
        public void exec(Abonent abonent) {
            ((Testy)abonent).gotTheMessage = true;
        }
    }

    @Test
    public void testExecForAbonent() throws Exception {
        MessageSystemImpl system = new MessageSystemImpl();


        Testy obj = new Testy();
        system.addService(obj, "name");
        Address address = system.getAddressByName("name");
        system.putMsg(address, new MsgToTesty(address, address));
        system.execForAbonent(obj);
        Assert.assertTrue(obj.gotTheMessage);

    }
}
