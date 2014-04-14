package accountService;

import base.MessageSystem;
import messageSystem.MessageSystemImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: lena
 * Date: 4/5/14
 * Time: 8:59 PM
 */
public class AccountServiceImplTest {
    private MessageSystem msg = new MessageSystemImpl();
    private AccountServiceImpl service = new AccountServiceImpl(msg);



    @Test
    public void testGetUserId() throws Exception {
        service.getUserId("Nick", "password");
        Assert.assertEquals(service.getUserId("Oven", "pass"), 2);
        Assert.assertEquals(service.getUserId("Nick", "password"), 1);

    }

    @Test
    public void testGetMessageSystem() throws Exception {
        Assert.assertEquals(service.getMessageSystem(), msg);

    }

}
