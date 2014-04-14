package resource;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by julia on 10.04.14.
 */
public class TestResourseFactory {
    private ResourceFactory resourceFactory;


    @BeforeMethod
    public void setUp() throws Exception {
        resourceFactory = ResourceFactory.instanse();
    }

    @Test
    public void testInstance() throws Exception {
        ResourceFactory tempResourceFactory = null;
        tempResourceFactory = ResourceFactory.instanse();
        Assert.assertEquals(tempResourceFactory, resourceFactory);
    }

    @Test
    public void testGetResource() throws Exception {
        Assert.assertNotNull(resourceFactory.getResource("settings/gameSettings.xml"));
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }
}
