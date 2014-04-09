package gameClasses;

import junit.framework.Assert;
import org.testng.annotations.Test;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 1:24 AM

 */
public class StrokeTest {
    @Test
    public void testGetInverse() throws Exception {
        Stroke stroke1 = new Stroke(1,2,3,2, "bom", "b");
        Stroke exp = new Stroke(6,5,4,5,"bom","w");
        Assert.assertEquals(stroke1.getInverse().getColor(), exp.getColor());
        Assert.assertEquals(stroke1.getInverse().getFrom_X(), exp.getFrom_X());
        Assert.assertEquals(stroke1.getInverse().getTo_X(), exp.getTo_X());
        Assert.assertEquals(stroke1.getInverse().getFrom_Y(), exp.getFrom_Y());
        Assert.assertEquals(stroke1.getInverse().getTo_Y(), exp.getTo_Y());
        Assert.assertEquals(stroke1.getInverse().getNext(), exp.getNext());

        Assert.assertEquals(exp.getInverse().getColor(), stroke1.getColor());
        Assert.assertEquals(exp.getInverse().getFrom_X(), stroke1.getFrom_X());
        Assert.assertEquals(exp.getInverse().getTo_X(), stroke1.getTo_X());
        Assert.assertEquals(exp.getInverse().getFrom_Y(), stroke1.getFrom_Y());
        Assert.assertEquals(exp.getInverse().getTo_Y(), stroke1.getTo_Y());
        Assert.assertEquals(exp.getInverse().getNext(), stroke1.getNext());

    }

    @Test
    public void testIsEmpty() throws Exception {
        Stroke stroke = new Stroke();
        Assert.assertTrue(stroke.isEmpty());
        stroke.setTo_X(1);
        Assert.assertFalse(stroke.isEmpty());
        stroke.fullSet(-1,1,-1,-1);
        Assert.assertFalse(stroke.isEmpty());
        stroke.fullSet(-1,-1,1,-1);
        Assert.assertFalse(stroke.isEmpty());
        stroke.fullSet(-1,-1,-1,1);
        Assert.assertFalse(stroke.isEmpty());
    }
}
