package frontend;

import base.Address;
import base.Frontend;
import base.MessageSystem;
import chat.ChatWSImpl;
import dbService.MsgUpdateUsers;
import dbService.UserDataSet;
import gameMechanic.gameCreating.MsgCreateGames;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.mockito.ArgumentCaptor;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import resource.Rating;
import utils.TimeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.*;

/**
 * Created by julia on 10.04.14.
 */
public class TestUserDataImpl {


    MessageSystem messageSystem;
    UserDataImpl userDataImpl;
    ChatWSImpl chatWSImpl;
    RemoteEndpoint remoteEndpoint;
    Address address;
    TimeHelper timeHelper;
    // Session session;
    int idFirst, idSecond, ratingFirst, ratingSecond;
    String sessionIdFirst, sessionIdSecond;
    UserDataSet userDataSet;


    @BeforeTest
    public void setUp() {
        messageSystem = mock(MessageSystem.class);
        userDataImpl = new UserDataImpl(messageSystem);
        chatWSImpl = mock(ChatWSImpl.class);
        remoteEndpoint = mock(RemoteEndpoint.class);
        address = mock(Address.class);
        timeHelper = mock(TimeHelper.class);
        // session = mock(Session.class);
        userDataSet = mock(UserDataSet.class);
    }

    @Test
    public void testGetAddress() {
        Assert.assertNotNull(userDataImpl.getAddress());
    }

    @Test
    public void testGetStartServerTime() {
        Assert.assertNotNull(UserDataImpl.getStartServerTime());
    }

    @Test
    public void testStart() throws Exception {
        Thread th = new Thread(this.userDataImpl);
        th.start();
        try {th.sleep(0);}
        catch (InterruptedException e) {}
    }

    @Test
    public void testGetSessionIdByUserId() {
        idSecond = 37;
        idFirst = 36;
        sessionIdFirst = "123456787654";
        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(idFirst);
        userDataImpl.putLogInUser(sessionIdFirst, userDataSet);

        Assert.assertEquals(sessionIdFirst, userDataImpl.getSessionIdByUserId(idFirst));
        Assert.assertNull(userDataImpl.getSessionIdByUserId(idSecond));
    }

    @Test
    public void testPutSessionIdAndWS() {
        sessionIdFirst = "1234567";
        UserDataSet userDataSet = mock(UserDataSet.class);

        userDataImpl.putLogInUser(sessionIdFirst, userDataSet);
        userDataImpl.putSessionIdAndChatWS(sessionIdFirst, chatWSImpl);
        org.eclipse.jetty.websocket.api.Session session = mock(org.eclipse.jetty.websocket.api.Session.class);
        when(chatWSImpl.getSession()).thenReturn(session);
        when(((org.eclipse.jetty.websocket.api.Session) session).getRemote()).thenReturn(remoteEndpoint);
        Assert.assertEquals(remoteEndpoint, userDataImpl.getChatWSBySessionId(sessionIdFirst));

    }

    @Test
    public void testGetWSBySessionId() {
        sessionIdFirst = "456765";
        Assert.assertNull(userDataImpl.getWSBySessionId(sessionIdFirst));

        org.eclipse.jetty.websocket.api.Session session = mock(org.eclipse.jetty.websocket.api.Session.class);
        WebSocketImpl webSocket = mock(WebSocketImpl.class);
        when(webSocket.getSession()).thenReturn(session);
        when(((org.eclipse.jetty.websocket.api.Session) session).getRemote()).thenReturn(remoteEndpoint);
        userDataImpl.putSessionIdAndWS(sessionIdFirst, webSocket);
        Assert.assertEquals(remoteEndpoint, userDataImpl.getWSBySessionId(sessionIdFirst));
    }

    @Test
    public  void testGetChatWSBySessionId() {
        sessionIdFirst = "123456776543";
        org.eclipse.jetty.websocket.api.Session session = mock(org.eclipse.jetty.websocket.api.Session.class);
        when(chatWSImpl.getSession()).thenReturn(session);
        when(((org.eclipse.jetty.websocket.api.Session) session).getRemote()).thenReturn(remoteEndpoint);
        userDataImpl.putSessionIdAndChatWS(sessionIdFirst, chatWSImpl);
        Assert.assertEquals(remoteEndpoint, userDataImpl.getChatWSBySessionId(sessionIdFirst));
        Assert.assertNull(userDataImpl.getChatWSBySessionId(sessionIdSecond));
    }

    @Test
    public void testGetOldUserSessionId() {
        idFirst = 34;
        sessionIdFirst = "73467987543";
        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(idFirst);
        userDataImpl.putLogInUser(sessionIdFirst, userDataSet);
        userDataImpl.putSessionIdAndUserSession(sessionIdFirst, userDataSet);

        sessionIdSecond = "56789098765434";
        userDataImpl.putLogInUser(sessionIdSecond, userDataSet);
        Assert.assertTrue(userDataImpl.getOldUserSessionId(idFirst) != null);
    }

    @Test
    public void testUpdateUserId() throws Exception{

        String sessionId = "3456787654";
        int id = 900;
        int id1 = 901;
        UserDataImpl userData1 = mock(UserDataImpl.class);
        UserDataSet userDataSet2 = new UserDataSet(id, "Nick1", 2, 3, 5);
        userData1.putSessionIdAndUserSession(sessionId, userDataSet2);
        userData1.updateUserId(sessionId, userDataSet2);
        UserDataSet userDataSet3 = new UserDataSet(id1, "Nick2", 3, 4, 5);
        userDataImpl.updateUserId(sessionId, userDataSet3);

        Assert.assertEquals("Nick2", userDataImpl.getUserSessionBySessionId(sessionId).getNick());

    }

    @Test
    public void testCreateGame() {

        sessionIdFirst = "123";
        UserDataSet userDataSetFirst = mock(UserDataSet.class);
        userDataImpl.playerWantToPlay(sessionIdFirst, userDataSetFirst);

        sessionIdSecond = "457";
        UserDataSet userDataSetSecond = mock(UserDataSet.class);
        userDataImpl.playerWantToPlay(sessionIdSecond, userDataSetSecond);

        userDataImpl.createGames();
        ArgumentCaptor<MsgCreateGames> msgCreateGames = ArgumentCaptor.forClass(MsgCreateGames.class);

        UserDataImpl userData = mock(UserDataImpl.class);
        verify(userData, never()).createGames();

    }

    @Test
    public void testKeepAlive() throws Exception{
        sessionIdFirst = "1234566543ew";
        UserDataImpl userData = mock(UserDataImpl.class);
        userData.keepAlive(sessionIdFirst);

        WebSocketImpl webSocket = mock(WebSocketImpl.class);
        UserDataSet userDataSet = mock(UserDataSet.class);
        userData.putLogInUser(sessionIdSecond, userDataSet);
        userData.putSessionIdAndWS(sessionIdSecond, webSocket);
        userData.keepAlive(sessionIdSecond);

    }




    @Test
    public void testGetLogInUserBySessionId() {
        idFirst = 98;
        Assert.assertNull(userDataImpl.getSessionIdByUserId(idFirst));

        sessionIdFirst = "56787654";
        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(idFirst);
        userDataImpl.putLogInUser(sessionIdFirst, userDataSet);
        Assert.assertEquals(sessionIdFirst, userDataImpl.getSessionIdByUserId(idFirst));
    }

    @Test
    public void testPartyEnd() throws Exception{

        userDataImpl.putSessionIdAndChatWS("1", new ChatWSImpl());
        userDataImpl.putSessionIdAndChatWS("2", new ChatWSImpl());
        try {
            userDataImpl.partyEnd(1, 2);
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }


    }

    @Test
    public void testCheckersUsers() {
        sessionIdFirst = "256y4387654";
        UserDataSet userDataSet = mock(UserDataSet.class);
        userDataImpl.putSessionIdAndUserSession(sessionIdFirst, userDataSet);
        userDataImpl.checkUsers(1);
        userDataImpl.checkUsers(0);
    }

    @Test
    public void testPartyEndWithNotEqRating() throws Exception {
        when(userDataSet.getId()).thenReturn(777);
        UserDataImpl.putSessionIdAndUserSession("sessionId", userDataSet);
        UserDataImpl.putLogInUser("sessionId", userDataSet);
        when(userDataSet.getRating()).thenReturn(666);

        UserDataSet userDataSetNew = mock(UserDataSet.class);
        when(userDataSetNew.getId()).thenReturn(888);
        UserDataImpl.putSessionIdAndUserSession("sessionIdNew", userDataSetNew);
        UserDataImpl.putLogInUser("sessionIdNew", userDataSetNew);
        when(userDataSetNew.getRating()).thenReturn(228);
        userDataImpl.partyEnd(888, 777);
    }

    @Test
    public void testPartyEndWithEqRating() throws Exception {
        when(userDataSet.getId()).thenReturn(777);
        UserDataImpl.putSessionIdAndUserSession("sessionId", userDataSet);
        UserDataImpl.putLogInUser("sessionId", userDataSet);

        UserDataSet userDataSetNew = mock(UserDataSet.class);
        when(userDataSetNew.getId()).thenReturn(888);
        UserDataImpl.putSessionIdAndUserSession("sessionIdNew", userDataSetNew);
        UserDataImpl.putLogInUser("sessionIdNew", userDataSetNew);
        userDataImpl.partyEnd(888, 777);
    }

    @Test
    public void testPartyEndWithNullSessions() throws Exception {
        when(userDataSet.getId()).thenReturn(777);
        UserDataImpl.putLogInUser("sessionId", userDataSet);

        UserDataSet userDataSetNew = mock(UserDataSet.class);
        when(userDataSetNew.getId()).thenReturn(888);
        UserDataImpl.putLogInUser("sessionIdNew", userDataSetNew);
        userDataImpl.partyEnd(888, 777);
    }

    @AfterTest
    public void tearDown() {

    }

}
