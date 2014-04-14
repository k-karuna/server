package resource;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by julia on 10.04.14.
 */
public class TestGameSettings {



        GameSettings gameSettings = new GameSettings(8, 2);

        @BeforeMethod
        public void setUp() throws Exception {

        }

        @Test
        public void testGameSettings() {
            Assert.assertEquals(gameSettings.getFieldSize(), 8);
            Assert.assertEquals(gameSettings.getPlayerSize(), 2);
        }

        @AfterMethod
        public void tearDown() throws Exception{

        }


}
