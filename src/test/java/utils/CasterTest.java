package utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: lena
 * Date: 4/8/14
 * Time: 11:47 PM
 */
public class CasterTest {
    @Test
    public void testCastKeysToStrings() throws Exception {
        Map<String, Integer> test = new HashMap<String, Integer>();
        test.put("k1",1);
        test.put("banderlogi",100500);
        String expected[] = {"banderlogi", "k1"};
        Assert.assertEquals(Caster.castKeysToStrings(test),expected);


    }
}
