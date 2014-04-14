package messageSystem;

import accountService.AccountServiceImpl;
import base.AddressService;
import base.MessageSystem;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: lena
 * Date: 4/5/14
 * Time: 9:10 PM

 */
public class AddressServiceImplTest {

    private MessageSystem msg = new MessageSystemImpl();
    AddressService service = new AddressServiceImpl();



    @Test
    public void testAddService() throws Exception {
        AccountServiceImpl service1 = new AccountServiceImpl(msg);

        service.addService(service1 ,"name1");
        Assert.assertNotEquals(service.getAddressByName("name1").getAbonentId(), 0);
        service.addService(service1 ,"name1");
        Assert.assertNotEquals(service.getAddressByName("name1").getAbonentId(), 0);


    }


}
