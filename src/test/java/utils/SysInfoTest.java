package utils;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**

 * User: lena
 * Date: 4/8/14
 * Time: 8:42 PM

 */
public class SysInfoTest {

    public final String testFile = "statistic/ccu";
    SysInfo sysInfo;
    @BeforeMethod
    public void setUp() throws Exception {
        sysInfo = new SysInfo();
        new File("statistic/memoryUsage").delete();
        new File("statistic/totalMemory").delete();
        new File("statistic/time").delete();
        new File("statistic/ccu").delete();

    }
    @Test
    public void testRun() throws Exception {
        SysInfo info = new SysInfo();

        Field f = SysInfo.class.getDeclaredField("data");
        f.setAccessible(true);
        Map<String, String> data =  (HashMap<String, String>)f.get(info);
        data.put("smth", "/statistic/smth");
        f.set(info, data);
        f.setAccessible(false);
        f = SysInfo.class.getDeclaredField("shouldLoop");
        f.setAccessible(true);
        f.set(info, 5);
        f.setAccessible(false);
        info.run();

        Assert.assertEquals(new File("statistic/memoryUsage").exists(), true);
        Assert.assertEquals(new File("statistic/totalMemory").exists(), true);
        Assert.assertEquals(new File("statistic/time").exists(), true);
        Assert.assertEquals(new File("statistic/ccu").exists(), true);
        Assert.assertEquals(new File("statistic/smth").exists(), false);
   //     thread.interrupt();
    }


}
