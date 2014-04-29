package gameMechanic;

import gameClasses.Field;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.VFS;

import java.io.File;
import java.lang.reflect.Method;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**

 * User: lena
 * Date: 4/9/14
 * Time: 6:24 PM

 */
public class GameSessionTest {

    GameSession gameSession;

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
        File a = new File("log");
        a.mkdir();
        String path = "log/AI";
        File file = new File(path);
        file.mkdir();

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
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("currentPositions");
        f.setAccessible(true);
        Field[][] fs = (Field[][])f.get(gs);
        fs[4][7] = new Field(Field.checker.white);
        f.set(gs, fs);
        Assert.assertTrue((Boolean) m.invoke(gs, Field.checker.black));
        fs[2][2] = new Field(Field.checker.black);

        f.set(gs, fs);
        f.setAccessible(false);
        Assert.assertTrue((Boolean) m.invoke(gs, Field.checker.white));
        gs = new GameSession(1,2,3,3);
        Assert.assertFalse((Boolean) m.invoke(gs, Field.checker.white));
        m.setAccessible(false);

    }

    @Test
    public void testWhiteLose() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("whiteLose");
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs));
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("whiteQuantity");
        f.setAccessible(true);
        f.set(gs,0);
        f.setAccessible(false);
        Assert.assertTrue((Boolean) m.invoke(gs));
        gs = new GameSession(1,2,3,3);
        Assert.assertTrue((Boolean) m.invoke(gs));
        gs = new GameSession(1,2,1,3);
        Assert.assertTrue((Boolean) m.invoke(gs));
        m.setAccessible(false);

    }

    @Test
    public void testBlackLose() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("blackLose");
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs));
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("blackQuantity");
        f.setAccessible(true);
        f.set(gs,0);
        f.setAccessible(false);
        Assert.assertTrue((Boolean) m.invoke(gs));
        gs = new GameSession(1,2,3,3);
        Assert.assertTrue((Boolean) m.invoke(gs));
        f = GameSession.class.getDeclaredField("currentPositions");
        f.setAccessible(true);
        Field[][] fs = (Field[][])f.get(gs);
        fs[0][0] = new Field(Field.checker.black);
        f.set(gs, fs);
        f.setAccessible(false);
        Assert.assertTrue((Boolean) m.invoke(gs));
        m.setAccessible(false);
    }

    @Test
    public void testNormal() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("normal", Integer.TYPE);
        m.setAccessible(true);
        Assert.assertEquals(m.invoke(gs, 0), 0);
        Assert.assertEquals( m.invoke(gs, -5),-1);
        m.setAccessible(false);

    }

    @Test
    public void testInBorder() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("inBorder", Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse((Boolean) m.invoke(gs, 15));
        Assert.assertFalse( (Boolean)m.invoke(gs, -5));
        Assert.assertTrue((Boolean) m.invoke(gs, 1));
        m.setAccessible(false);

    }

    @Test
    public void testStandartCheck() throws Exception {
                GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("standartCheck", Integer.TYPE,Integer.TYPE,
                Integer.TYPE,Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 4, 1, 3));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 3, 1, 4));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 4, 1, 4));
        Assert.assertFalse( (Boolean)m.invoke(gs, -1, 1, 3, 3));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, -1, 3, 3));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 1, -3, 3));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 1, 3, -3));
        Assert.assertFalse( (Boolean)m.invoke(gs, 0, 0, 2, 2));
        m.setAccessible(false);

    }

    @Test
    public void testChecking() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("checking", Integer.TYPE,Integer.TYPE,
                Integer.TYPE,Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse( (Boolean)m.invoke(gs,2, 1, 4, 1, 3));
        Assert.assertFalse( (Boolean)m.invoke(gs,1, 1, 4, 1, 3));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 1, 7, 1, 3));
        Assert.assertTrue((Boolean) m.invoke(gs, 1, 0, 2, 1, 3));

        m.setAccessible(false);

    }

    @Test
    public void testBecameKing() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("becameKing", Integer.TYPE,Integer.TYPE);
        m.setAccessible(true);
        Assert.assertFalse( (Boolean)m.invoke(gs,0, 1));
        Assert.assertFalse( (Boolean)m.invoke(gs,0, 0));
        Assert.assertFalse( (Boolean)m.invoke(gs, 1, 7));
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("currentPositions");
        f.setAccessible(true);
        Field[][] fs = (Field[][])f.get(gs);
        fs[0][0] = new Field(Field.checker.black);
        f.set(gs, fs);
        Assert.assertTrue((Boolean) m.invoke(gs, 0, 0));
        fs[7][1] = new Field(Field.checker.white);
        f.set(gs, fs);
        Assert.assertTrue((Boolean) m.invoke(gs, 1, 7));
        f.setAccessible(false);
        m.setAccessible(false);

    }

    @Test
    public void testBlackWin() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("blackWin", Long.TYPE);
        m.setAccessible(true);
        Assert.assertFalse( (Boolean)m.invoke(gs, 1));
        Assert.assertTrue((Boolean) m.invoke(gs, 99999999999l));
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("lastStroke");
        f.setAccessible(true);
        f.set(gs,1);
        Assert.assertFalse( (Boolean)m.invoke(gs, 1));
        Assert.assertFalse( (Boolean)m.invoke(gs, 99999999999l));
        f.setAccessible(false);
        m.setAccessible(false);
    }

    @Test
    public void testWhiteWin() throws Exception {
        GameSession gs = new GameSession(1,2,8,3);
        Method m = GameSession.class.getDeclaredMethod("whiteWin", Long.TYPE);
        m.setAccessible(true);
        Assert.assertFalse( (Boolean)m.invoke(gs, 1));
        Assert.assertFalse((Boolean) m.invoke(gs, 99999999999l));
        java.lang.reflect.Field f = GameSession.class.getDeclaredField("lastStroke");
        f.setAccessible(true);
        f.set(gs,1);
        Assert.assertFalse( (Boolean)m.invoke(gs, 1));
        Assert.assertTrue((Boolean) m.invoke(gs, 99999999999l));
        f.setAccessible(false);
        m.setAccessible(false);
    }

    @Test
    public void testCheckOtherEatingOpportunity() throws Exception {

        GameSession gameSession = new GameSession(1, 2);
        Assert.assertEquals(false, gameSession.checkOtherEatingOpportunity(0, -1, 1, 1, 2, 3));
        Assert.assertEquals(true, gameSession.checkOtherEatingOpportunity(2, 2, 0, 0, 3, 3));
    }

    @Test
    public void testKingEatRightUp() {

        GameSession gameSession = new GameSession(1, 2);
        Assert.assertEquals(false, gameSession.kingCanEatRightUp(1, 1));
        gameSession.move(1, 1, 5, 5);
        Assert.assertEquals(false, gameSession.kingCanEatRightUp(4, 6));
        Assert.assertEquals(false, gameSession.kingCanEatRightUp(4, 4));

    }

    @Test
    public void testKingCanEatLeftUp() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.move(1, 1, 5, 5);
        Assert.assertEquals(false, gameSession.kingCanEatLeftUp(3, 5));
        Assert.assertEquals(false, gameSession.kingCanEatLeftUp(0, 0));
        gameSession.getField(0, 0).setType(Field.checker.black);
        Assert.assertEquals(false, gameSession.kingCanEatLeftUp(0, 0));

        gameSession.getField(0, 0).setType(Field.checker.white);
        Assert.assertEquals(false, gameSession.kingCanEatLeftUp(0, 0));

    }

    @Test
    public void testKingCanEatRightDown() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(7, 1).setType(Field.checker.black);
        gameSession.getField(0, 1).setType(Field.checker.black);
        gameSession.getField(0, 0).setType(Field.checker.black);
        Assert.assertEquals(false, gameSession.kingCanEatRightDown(7, 1));
        Assert.assertEquals(false, gameSession.kingCanEatRightDown(0, 1));
        Assert.assertEquals(false, gameSession.kingCanEatRightDown(0, 0));

        gameSession.getField(0, 0).setType(Field.checker.white);
        Assert.assertEquals(false, gameSession.kingCanEatRightDown(0, 0));

    }

    @Test
    public void testKingCanEatLeftDown() {
        GameSession gameSession = new GameSession(1, 2);
        Assert.assertEquals(false, gameSession.kingCanEatRightDown(1, 1));

    }

    @Test
    public void canEatKingTopRightTest() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(7, 6).setType(Field.checker.black);
        gameSession.getField(6, 5).setType(Field.checker.white);
        gameSession.getField(6, 5).makeKing();

        Assert.assertFalse(gameSession.canEat(6, 5));

        gameSession.getField(6, 7).setType(Field.checker.black);
        gameSession.getField(5, 6).setType(Field.checker.white);
        gameSession.getField(5, 6).makeKing();

        Assert.assertFalse(gameSession.canEat(5, 6));

        gameSession.getField(4, 5).setType(Field.checker.white);
        gameSession.getField(3, 4).setType(Field.checker.white);
        gameSession.getField(3, 4).makeKing();

        Assert.assertFalse(gameSession.canEat(3, 4));
    }

    @Test
    public void testCanEat() {
        GameSession gameSession = new GameSession(1,2);
        Assert.assertTrue(gameSession.canEat(7, 4));
        gameSession.checkStroke(1, 6, 5, 7, 4);
        Assert.assertTrue(gameSession.canEat(1, 4));
        gameSession.checkStroke(2, 2, 5, 1, 4);
        Assert.assertTrue(gameSession.canEat(5, 2));
        gameSession.checkStroke(1, 7, 4, 5, 2);
    }

    @Test
    public void testCheckEating() {
        GameSession gameSession = new GameSession(1, 2);
        Assert.assertTrue(gameSession.checkEating(2, 2, 3, 3));
        gameSession.fieldIsKing(2, 2);
        Assert.assertTrue(gameSession.checkKingOtherEating(2, 2, 3, 3));

    }

    @Test
    public void testCheckStroke() {
        GameSession gameSession = spy(new GameSession(1, 2));
        when(gameSession.checkKingOtherEating( 1, 1, 2, 2)).thenReturn(Boolean.FALSE);
        when(gameSession.fieldIsKing(1, 1)).thenReturn(Boolean.TRUE);
        Boolean aBoolean = gameSession.checkEating( 1, 1, 2, 2);
        Assert.assertTrue(gameSession.checkEating(1, 1, 2, 2));
    }

    @Test
    public void testMakeUsualStroke() {
        GameSession gameSession = new GameSession(1, 2);
        Assert.assertEquals(true, gameSession.makeUsualStroke(0, 0, 0, 0));
        Assert.assertEquals(false, gameSession.makeUsualStroke(0, 0, 0, 0));
    }

   @Test
    public void testGetWinnerId() {
       GameSession gameSession = new GameSession(1, 2);
       Assert.assertEquals(0, gameSession.getWinnerId());
   }

    @Test
    public void testPawnEat() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(3, 3).setType(Field.checker.black);
        gameSession.getField(2, 2).setType(Field.checker.white);
        gameSession.getField(2, 2).makeKing();

        Assert.assertFalse(gameSession.checkStroke(1, 2, 5, 4, 3));
    }

    @Test
    public void testPawnStrokeOrCanEat() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(3, 3).setType(Field.checker.black);
        gameSession.getField(2, 2).setType(Field.checker.white);
        gameSession.getField(6, 0).setType(Field.checker.white);

        Assert.assertFalse(gameSession.checkStroke(1, 0, 1, 1, 0));
    }

    @Test
    public void testPawnLeftBottomEat() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(3, 3).setType(Field.checker.white);
        gameSession.getField(2, 2).setType(Field.checker.black);

        Assert.assertFalse(gameSession.checkStroke(1, 3, 4, 1, 6));
    }

    @Test
    public void testPawnRightBottomEat() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(3, 3).setType(Field.checker.white);
        gameSession.getField(2, 4).setType(Field.checker.black);

        Assert.assertFalse(gameSession.checkStroke(1, 3, 4, 5, 6));
    }

    @Test
    public void testKingStrokeRightTop() {
        GameSession gameSession = new GameSession(1, 2);
        gameSession.getField(1, 3).setType(Field.checker.white);
        gameSession.getField(4, 6).setType(Field.checker.black);
        gameSession.getField(1, 3).makeKing();

        Assert.assertFalse(gameSession.checkStroke(1, 3, 6, 7, 2));
    }

    @Test
    public void testCheckStrokeNew() {

        GameSession gameSession = new GameSession(1, 2, 8, 3);
        Assert.assertFalse( gameSession.checkStroke(1,7,6,8,5));
        Assert.assertFalse(gameSession.checkStroke(1,1,2,0,3));
        Assert.assertTrue(gameSession.checkStroke(1, 0, 5, 2, 3));

        Assert.assertFalse(gameSession.checkStroke( 1, 0, 5, 0, 4));
        Assert.assertFalse(gameSession.checkStroke( 1, 0, 5, 1, 5));
        Assert.assertFalse(gameSession.checkStroke( 1, 1, 5, 0, 5));
        Assert.assertFalse(gameSession.checkStroke(1, 0, 5, 0, 5));
        Assert.assertFalse(gameSession.checkStroke(1, 0, 5, 1, 4));
        Assert.assertFalse(gameSession.checkStroke(2, 0, 5, 1, 4));
    }


}
