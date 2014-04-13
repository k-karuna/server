/**
 * Created by julia on 08.04.14.
 */
package resource;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resource.Rating;

public class TestRating {

    @BeforeMethod
    public void setUp() throws Exception {
        Rating.avgDiff = 10;
        Rating.maxDiff = 15;
        Rating.minDiff = 5;
        Rating.decreaseThreshold = 0;
    }

    @Test
    public void getDiffTestThresholdNotNull() {
        Rating.decreaseThreshold = 4;
        Assert.assertEquals(Rating.getDiff(2, 10), 12);
    }

    @Test
    public void getDiffTestThresholdIsNull() {
        Assert.assertEquals(Rating.getDiff(10, 5), 10);
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }
}

