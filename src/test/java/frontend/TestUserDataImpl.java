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


        @BeforeTest
        public void setUp() {
            messageSystem = mock(MessageSystem.class);
            userDataImpl = new UserDataImpl(messageSystem);
            chatWSImpl = mock(ChatWSImpl.class);
            remoteEndpoint = mock(RemoteEndpoint.class);
            address = mock(Address.class);
            timeHelper = mock(TimeHelper.class);
            // session = mock(Session.class);
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

/*    @Test
    public void testGetOldUserSessionId() {
        idFirst = 34;
        sessionIdFirst = "23467987543";
        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(idFirst);
        userDataImpl.putLogInUser(sessionIdFirst, userDataSet);
        userDataImpl.putSessionIdAndUserSession(sessionIdFirst, userDataSet);

        sessionIdSecond = "56789098765434";
        userDataImpl.putLogInUser(sessionIdSecond, userDataSet);
        Assert.assertEquals(sessionIdFirst, userDataImpl.getOldUserSessionId(idFirst));
    }*/

    @Test
    public void testUpdateUserId() throws Exception{

        String sessionId = "3456787654";
        UserDataImpl userData1 = mock(UserDataImpl.class);
        UserDataSet userDataSet2 = mock(UserDataSet.class);
        userData1.putSessionIdAndUserSession(sessionId, userDataSet2);
        userData1.updateUserId(sessionId, userDataSet2);

        idFirst = 57;
        sessionIdFirst = "256789438w762";
        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(idFirst);
        UserDataImpl userData = mock(UserDataImpl.class);
        userData.putLogInUser(sessionIdFirst, userDataSet);
        userData.putSessionIdAndUserSession(sessionIdFirst, userDataSet);

        UserDataSet userDataSet1 = mock(UserDataSet.class);
        when(userDataSet1.getId()).thenReturn(idFirst);
        userData.putSessionIdAndUserSession(sessionIdFirst, userDataSet1);
        userData.updateUserId(sessionIdFirst, userDataSet1);



       /* UserDataSet userDataSetSecond = new UserDataSet(34, "Julia", 8, 100, 80);
        when(userDataSetSecond.getId()).thenReturn(34);
        when(userDataSetSecond.getRating()).thenReturn(80);
        when(userDataSetSecond.getNick()).thenReturn("Julia");
        sessionIdSecond = "45678987654";
        userData.putLogInUser(sessionIdSecond, userDataSetSecond);
        userData.putSessionIdAndUserSession(sessionIdSecond, userDataSetSecond);
        userData.updateUserId(sessionIdSecond, userDataSetSecond);
        verify(userData).getUserSessionBySessionId(sessionIdFirst);
*/
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
//            verify(messageSystem).putMsg((base.Address) isNull(), msgCreateGames.capture());

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

        idFirst = 38;
        ratingFirst = 10;
        UserDataSet userDataSetFirst = mock(UserDataSet.class);
        when(userDataSetFirst.getId()).thenReturn(idFirst);
        when(userDataSetFirst.getRating()).thenReturn(ratingFirst);
        ChatWSImpl chatWSFirst = mock(ChatWSImpl.class);
        /*userDataImpl.putLogInUser(null, userDataSetFirst);
        userDataImpl.putSessionIdAndChatWS(null, chatWSFirst);
        userDataImpl.putSessionIdAndUserSession(null, userDataSetFirst);*/
        idSecond = 56;
        ratingSecond = 10;
        UserDataSet userDataSetSecond = mock(UserDataSet.class);
        when(userDataSetSecond.getId()).thenReturn(idSecond);
        when(userDataSetSecond.getRating()).thenReturn(ratingSecond);
        ChatWSImpl chatWSSecond = mock(ChatWSImpl.class);
//        userDataImpl.putLogInUser(null, userDataSetSecond);
//        userDataImpl.putSessionIdAndChatWS(null, chatWSSecond);
//        userDataImpl.putSessionIdAndUserSession(null, userDataSetSecond);
        userDataImpl.partyEnd(idFirst, idSecond);

        sessionIdFirst = "12345678765432";
        userDataImpl.putLogInUser(sessionIdFirst, userDataSetFirst);
        userDataImpl.putSessionIdAndChatWS(sessionIdFirst, chatWSFirst);
        userDataImpl.putSessionIdAndUserSession(sessionIdFirst, userDataSetFirst);
        sessionIdSecond = "456787654";
        userDataImpl.putLogInUser(sessionIdSecond , userDataSetSecond);
        userDataImpl.putSessionIdAndChatWS(sessionIdSecond, chatWSSecond);
        userDataImpl.putSessionIdAndUserSession(sessionIdSecond, userDataSetSecond);
        userDataImpl.partyEnd(idFirst, idSecond);



    }

    @Test
    public void testCheckersUsers() {
        sessionIdFirst = "256y4387654";
        UserDataSet userDataSet = mock(UserDataSet.class);
        userDataImpl.putSessionIdAndUserSession(sessionIdFirst, userDataSet);
        userDataImpl.checkUsers(1);
        userDataImpl.checkUsers(0);
    }

        @AfterTest
        public void tearDown() {

        }

    }
