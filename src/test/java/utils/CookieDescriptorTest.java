package utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;

/**

 * User: lena
 * Date: 4/8/14
 * Time: 11:43 PM

 */
@Test
public class CookieDescriptorTest {
    public void testGetCookieByName() throws Exception {
        Cookie[] coo = {new Cookie("one", "one"), new Cookie("two", "two!")};
        CookieDescriptor desc = new CookieDescriptor(coo);
        Assert.assertEquals(desc.getCookieByName("one"),"one");
        Assert.assertEquals(desc.getCookieByName("three"), null);
    }
}
