package gameClasses;

import org.testng.Assert;
import org.testng.annotations.Test;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 1:18 AM

 */
public class FieldTest {
    @Test
    public void testIsEmpty() throws Exception {
        Field field = new Field();
        Assert.assertTrue(field.isEmpty());
        field.setType(Field.checker.black);
        Assert.assertFalse(field.isEmpty());

    }
}
