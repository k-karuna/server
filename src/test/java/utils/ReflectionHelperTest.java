package utils;

import gameClasses.Stroke;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 4/9/14
 * Time: 12:38 AM

 */
public class ReflectionHelperTest {
    @Test
    public void testSetFieldValue() throws Exception {

        Stroke stroke = new Stroke(1,2,2,1,"st","red");
        ReflectionHelper.setFieldValue(stroke, "to_x", "10");
        ReflectionHelper.setFieldValue(stroke, "color", "black");
        ReflectionHelper.setFieldValue(stroke, "next", "2");
        Assert.assertEquals(stroke.getColor(),"black");
        Assert.assertEquals(stroke.getTo_X(),10);
        Assert.assertEquals(stroke.getNext(),'0');

    }


}
