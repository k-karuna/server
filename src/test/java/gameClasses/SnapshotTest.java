package gameClasses;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * User: lena
 * Date: 4/9/14
 * Time: 2:25 PM
 */
public class SnapshotTest {
    Field[][] field;
    @BeforeMethod
    public void setUp() throws Exception {
        field = new Field[][]{
                {new Field(Field.checker.black), new Field(Field.checker.nothing)},
                { new Field(Field.checker.black), new Field(Field.checker.white)}
        };
        field[0][0].makeKing();
        field[1][1].makeKing();

    }

    @Test
    public void testToString() throws Exception {
        Snapshot snap = new Snapshot(field,'b',2,'0');
        String exp1 = "{\"status\":\"snapshot\",\"next\":\"0\",\"color\":\"b\",\"field\":[[\"black\", " +
         "\"nothing\"], [\"black\", \"white\"]],\"king\":[[\"true\", \"false\"], [\"false\", \"true\"]]}";
        Assert.assertEquals(snap.toString(), exp1);
        snap = new Snapshot(field,'w',2,'0');
        String exp2 = "{\"status\":\"snapshot\",\"next\":\"0\",\"color\":\"w\",\"field\":[[\"black\", " +
                "\"nothing\"], [\"black\", \"white\"]],\"king\":[[\"true\", \"false\"], [\"false\", \"true\"]]}";
        Assert.assertEquals(snap.toString(), exp2);

    }

    @Test
    public void testToStringTest() throws Exception {
        Snapshot snap = new Snapshot(field,'b',2,'0');
        String exp1 = "{\'status\':\'snapshot\',\'next\':\'0\',\'color\':\'b\',\'field\':[[\'black\', " +
                "\'nothing\'], [\'black\', \'white\']],\'king\':[[\'true\', \'false\'], [\'false\', \'true\']]}";
        Assert.assertEquals(snap.toStringTest(), exp1);
        snap = new Snapshot(field,'w',2,'0');
        String exp2 = "{\'status\':\'snapshot\',\'next\':\'0\',\'color\':\'w\',\'field\':[[\'black\', " +
                "\'nothing\'], [\'black\', \'white\']],\'king\':[[\'true\', \'false\'], [\'false\', \'true\']]}";
        Assert.assertEquals(snap.toStringTest(), exp2);



    }
}
