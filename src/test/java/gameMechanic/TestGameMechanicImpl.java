package gameMechanic;

import base.*;
import chat.GameChatImpl;
import com.jcraft.jsch.Session;
import dbService.UserDataSet;
import frontend.UserDataImpl;
import gameClasses.Stroke;
import messageSystem.MessageSystemImpl;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

/**
 * Created by julia on 13.04.14.
 */
public class TestGameMechanicImpl {

    GameMechanic gameMechanic;
    MessageSystem messageSystem;
    Session session;
    UserDataImpl userData;
    Map<String, UserDataSet> users;
    Map<String, String> sessionIdToColor;
    GameChat gameChat;

    int idFirst, idSecond;
    String nickFirst, nickSecond;

    Map<String, UserDataSet> usersPlayNobody = new HashMap<String, UserDataSet>();

    @BeforeTest
    public void setUp() {
        messageSystem = mock(MessageSystemImpl.class);
        gameMechanic = new GameMechanicImpl(messageSystem);
        session = mock(Session.class);
        userData = mock(UserDataImpl.class);
        users = new HashMap<String, UserDataSet>();
        gameChat = mock(GameChat.class);

        idFirst = 23;
        idSecond = 32;
        nickFirst = "Julia";
        nickSecond = "Lena";

        UserDataSet userDataSetFirst = new UserDataSet(idFirst, nickFirst, 80, 100, 20);
        UserDataSet userDataSetSecond = new UserDataSet(idSecond, nickSecond, 90, 100, 10);

        users.put(nickFirst, userDataSetFirst);
        users.put(nickSecond, userDataSetSecond);


    }

    @Test
    public void testCreateGames() {
        when(messageSystem.getAddressByName("GameChat")).thenReturn(new Address());
        sessionIdToColor = gameMechanic.createGames(users, false);
    //    Assert.assertEquals(2, sessionIdToColor.size());

        Assert.assertEquals(0, gameMechanic.createGames(usersPlayNobody, false).size());

    }

    @Test
    public void testCheckStroke() {
        Assert.assertEquals(0, gameMechanic.checkStroke(idFirst, new Stroke(1, 1, 1, 1, "  ")).size());

        Assert.assertEquals(0, gameMechanic.checkStroke(idSecond, new Stroke(2, 2, 2, 2, "lose")).size());

        sessionIdToColor = gameMechanic.createGames(users, false);
        Assert.assertTrue(gameMechanic.checkStroke(idFirst, new Stroke(1, 1, 3, 3, "")).containsKey(idFirst));

    }

    @Test
    public void testRemoveDeadGames() throws NoSuchFieldException, IllegalAccessException {

        MessageSystem ms = mock(MessageSystem.class);
        GameMechanicImpl gameMechanic1 = new GameMechanicImpl(ms);

        Map<Integer,GameSession> userIdToSession= new HashMap<Integer,GameSession>();
        userIdToSession.put(1, null);

        GameSession session = mock(GameSession.class);
        when(session.getWinnerId()).thenReturn(1);
        userIdToSession.put(2, session);

        java.lang.reflect.Field field = gameMechanic1.getClass().getDeclaredField("userIdToSession");
        field.setAccessible(true);
        field.set(gameMechanic1,userIdToSession);

        GameMechanicImpl gameMechanicSpy = spy(gameMechanic1);
        gameMechanicSpy.removeDeadGames();
        verify(gameMechanicSpy, atLeast(1)).removeDeadGames();
    }

    @Test
    public void testCreateGamesWithoutUsers() throws Exception {
        Map<String, UserDataSet> emptyUsers = new ConcurrentHashMap<String, UserDataSet>();

        Assert.assertEquals(gameMechanic.createGames(emptyUsers, false).size(), 0);
    }

    @Test
    public void testOnePlayerOnly() throws Exception {
        users.remove("secondPlayer");
        sessionIdToColor = gameMechanic.createGames(users, false);
        Assert.assertEquals(sessionIdToColor.size(), 0);
    }

    @Test
    public void testTwoPlayerGame() throws Exception {
        sessionIdToColor = gameMechanic.createGames(users, false);
        Assert.assertEquals(sessionIdToColor.size(), 0);
        String colors = "";
        for (String key: sessionIdToColor.keySet()) {
            Assert.assertNotNull(sessionIdToColor.get(key));
            colors += sessionIdToColor.get(key);
        }
        Assert.assertTrue(!colors.contains("white"));
        Assert.assertTrue(!colors.contains("black"));
    }

    @Test
    public void testMultipleGamesCreated() {

        users.put("thirdNick", new UserDataSet(2, "thirdNick", 500, 5, 5));
        users.put("forthNick", new UserDataSet(2, "forthNick", 500, 5, 5));
        users.put("fifthNick", new UserDataSet(2, "fifthNick", 500, 5, 5));

        sessionIdToColor = gameMechanic.createGames(users, false);
        Assert.assertEquals(sessionIdToColor.size(), 2);
        for (String key: sessionIdToColor.keySet()) {
            System.out.println(key + " " + sessionIdToColor.get(key));
        }

        sessionIdToColor = gameMechanic.createGames(users, false);
        Assert.assertEquals(sessionIdToColor.size(), 0);
    }

    @Test
    public void testGameWithChat() {
        GameChat gameChat = new GameChatImpl(messageSystem);

        sessionIdToColor = gameMechanic.createGames(users, true);
        Assert.assertEquals(sessionIdToColor.size(), 0);

    }

    @Test
    public void testLoose() throws Exception {
        sessionIdToColor = gameMechanic.createGames(users, false);
        Assert.assertEquals(sessionIdToColor.size(), 0);

        Map<Integer, Stroke> intToStroke;
        UserData userData = new UserDataImpl(messageSystem);
        intToStroke = gameMechanic.checkStroke(1, new Stroke(0,0,0,0, "lose"));
        Assert.assertEquals(intToStroke.size(), 0);

    }

    @AfterTest
    public void tearDown() {

    }
}
