package gameMechanic;

import gameClasses.Field;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.VFS;

import java.io.File;
import java.lang.reflect.Method;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 6:24 PM

 */
public class GameSessionTest {

    @Test
    public void testGetPlayerColor() throws Exception {
        Method m = GameSession.class.getDeclaredMethod("getPlayerColor", Integer.TYPE);
        m.setAccessible(true);
        GameSession gs = new GameSession(1,2);
        Assert.assertEquals(m.invoke(gs, 1), Field.checker.white);
        Assert.assertEquals(m.invoke(gs, 2), Field.checker.black);
        m.setAccessible(false);
    }

    @Test
    public void testGetAnotherColor() throws Exception {
        Method m = GameSession.class.getDeclaredMethod("getAnotherColor", Field.checker.class);
        m.setAccessible(true);
        GameSession gs = new GameSession(1,2);
        Assert.assertEquals(m.invoke(gs, Field.checker.black), Field.checker.white);
        Assert.assertEquals(m.invoke(gs, Field.checker.white), Field.checker.black);
        Assert.assertEquals(m.invoke(gs, Field.checker.nothing), Field.checker.nothing);
        m.setAccessible(false);
    }

    @Test
    public void testGetNext() throws Exception {
        GameSession gs = new GameSession(1,2);
        Assert.assertEquals(gs.getNext(), 'w');
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("lastStroke");
        f.setAccessible(true);
        f.set(gs, 1);
        f.setAccessible(false);
        Assert.assertEquals(gs.getNext(), 'b');
    }

    @Test
    public void testGetFields() throws Exception {
        GameSession gs = new GameSession(1,2);
        int[] fields = new int[]{0, 2, 4, 6, 9, 11, 13, 15, 16, 18, 20, 22,
                -41, 43, 45, 47, 48, 50, 52, 54, 57, 59, 61, 63};
        Method m = GameSession.class.getDeclaredMethod("makeKing", Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        m.invoke(gs, 0, 0);
        m.invoke(gs, 1, 5);
        m.setAccessible(false);
        Assert.assertEquals(gs.getFields(), fields);
    }

    @Test
    public void testSaveLog() throws Exception {
        GameSession gs = new GameSession(1,2);
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("dirForLog");
        f.setAccessible(true);
        f.set(gs, "test");
        f.setAccessible(false);
        String path = "log/AI";
        File file = new File(path);
        for (File f1 : file.listFiles())
            f1.delete();
        gs.saveLog(1);
        path = path + "/" + (file.listFiles())[0].getName();
        String data = VFS.readFile(path);
        Assert.assertEquals(data, "white");
        file.delete();
        gs.saveLog(2);
         data = VFS.readFile(path);
        Assert.assertEquals(data, "black");
    }

    @Test
    public void testGetSnapshot() throws Exception {
        GameSession gs = new GameSession(1,2,2,3);
        String snap1 = "{\"status\":\"snapshot\",\"next\":\"w\"," +
                "\"color\":\"w\",\"field\":[[\"white\", \"nothing\"], " +
                "[\"nothing\", \"white\"]],\"king\":[[\"false\", \"false\"]," +
                " [\"false\", \"false\"]]}";
        String snap2 = "{\"status\":\"snapshot\",\"next\":\"w\"," +
                "\"color\":\"b\",\"field\":[[\"white\", \"nothing\"]," +
                " [\"nothing\", \"white\"]],\"king\":[[\"false\", \"false\"]," +
                " [\"false\", \"false\"]]}";
        Assert.assertEquals(gs.getSnapshot(1).toString(), snap1);
        Assert.assertEquals(gs.getSnapshot(2).toString(), snap2);
    }

    @Test
    public void testCanMoveRightUp() throws Exception {
        GameSession gs = new GameSession(1,2,3,3);
        Method m = GameSession.class.getDeclaredMethod("canMoveRightUp", Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs, 0,0));
        Assert.assertFalse((Boolean) m.invoke(gs, 0,2));
        Assert.assertFalse((Boolean) m.invoke(gs, 2,0));
        gs = new GameSession(1,2,8,3);
        Assert.assertTrue((Boolean) m.invoke(gs, 2, 2));
        m.setAccessible(false);

    }

    @Test
    public void testCanMoveRightDown() throws Exception {
        GameSession gs = new GameSession(1,2,3,3);
        Method m = GameSession.class.getDeclaredMethod("canMoveRightDown", Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs, 0,0));
        Assert.assertFalse((Boolean) m.invoke(gs, 1,1));
        Assert.assertFalse((Boolean) m.invoke(gs, 2,2));
        gs = new GameSession(1,2,8,3);
        Assert.assertTrue((Boolean) m.invoke(gs, 1,5));
        m.setAccessible(false);
    }

    @Test
    public void testCanMoveLeftUp() throws Exception {
        GameSession gs = new GameSession(1,2,3,3);
        Method m = GameSession.class.getDeclaredMethod("canMoveLeftUp", Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs, 1,1));
        Assert.assertFalse((Boolean) m.invoke(gs, 0,0));
        Assert.assertFalse((Boolean) m.invoke(gs, 2,2));
        gs = new GameSession(1,2,8,3);
        Assert.assertTrue((Boolean) m.invoke(gs, 2,2));
        m.setAccessible(false);
    }

    @Test
    public void testCanMoveLeftDown() throws Exception {
        GameSession gs = new GameSession(1,2,3,3);
        Method m = GameSession.class.getDeclaredMethod("canMoveLeftDown", Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs, 1,1));
        Assert.assertFalse((Boolean) m.invoke(gs, 2,0));
        Assert.assertFalse((Boolean) m.invoke(gs, 0,2));
        gs = new GameSession(1,2,8,3);
        Assert.assertTrue((Boolean) m.invoke(gs, 1,5));
        m.setAccessible(false);
    }
    @Test
    public void testCanMove() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("canMove", Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        //чёрные
        Assert.assertFalse((Boolean) m.invoke(gs, 1,7));
        Assert.assertTrue((Boolean) m.invoke(gs, 1,5));
        Assert.assertTrue((Boolean) m.invoke(gs, 1,4));
        Assert.assertTrue((Boolean) m.invoke(gs, 7,5));

        //белые
        Assert.assertTrue((Boolean) m.invoke(gs, 0,2));
        Assert.assertTrue((Boolean) m.invoke(gs, 2,2));
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("currentPositions");
        f.setAccessible(true);
        Field[][] fs = (Field[][])f.get(gs);
        fs[3][7] = new Field(Field.checker.white);
        f.set(gs, fs);
        f.setAccessible(false);
        Assert.assertTrue((Boolean) m.invoke(gs, 7,3));
        Assert.assertFalse((Boolean) m.invoke(gs, 0, 0));
        m.setAccessible(false);
    }

    @Test
    public void testCanMoveClr() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("canMove", Field.checker.class);
        m.setAccessible(true);
        Assert.assertTrue((Boolean) m.invoke(gs, Field.checker.black));
        gs = new GameSession(1,2,3,3);
        Assert.assertFalse((Boolean) m.invoke(gs, Field.checker.white));



    }
}
