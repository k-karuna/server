package GameMechanicsTests;

import base.GameMechanic;
import base.MessageSystem;
import gameMechanic.GameMechanicImpl;
import messageSystem.MessageSystemImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by velikolepnii on 08.04.14.
 */
public class GMTest {
    private GameMechanic gameMechanic;
    private MessageSystem messageSystem;

    @BeforeMethod
    public void setUp() throws Exception {
        messageSystem = new MessageSystemImpl();
        gameMechanic = new GameMechanicImpl(messageSystem);
    }

    @Test
    public void test(){
        Assert.assertEquals(gameMechanic.getMessageSystem(), messageSystem);
    }
}
