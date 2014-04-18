package FrontendTest;

import base.Address;
import base.WebSocket;
import dbService.UserDataSet;
import frontend.UserDataImpl;
import frontend.WebSocketImpl;
import gameClasses.Stroke;
import messageSystem.MessageSystemImpl;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
 /**
 * Created by velikolepnii on 12.04.14.
 */
public class WebSocketImplTest {
    private MessageSystemImpl messageSystem = new MessageSystemImpl();
    @Test
    public void testonWebSocketText() {
        UserDataSet userDataSet = mock(UserDataSet.class);
        UserDataImpl.putLogInUser("1", userDataSet);
        WebSocketImpl websocket = spy(new WebSocketImpl(false));
        MessageSystemImpl msg = mock(MessageSystemImpl.class);
        when(msg.getAddressByName("GameMechanic")).thenReturn(new Address());
        websocket.setMS(msg);
        when(websocket.isNotConnected()).thenReturn(false);
        String time = UserDataImpl.getStartServerTime();
        websocket.onWebSocketText("{\"sessionId\":\"1\", \"startServerTime\":\"" + time + "\", \"from_x\":\"1\", \"from_y\":\"1\", \"to_x\":\"5\", \"to_y\":\"5\", \"status\":\"1\"}");
        verify(websocket).onWebSocketText("{\"sessionId\":\"1\", \"startServerTime\":\"" + time + "\", \"from_x\":\"1\", \"from_y\":\"1\", \"to_x\":\"5\", \"to_y\":\"5\", \"status\":\"1\"}");
    }
    @Test
    public void testonWebSocketTextNegative() throws Exception{
        UserDataSet userDataSet = mock(UserDataSet.class);
        UserDataImpl.putLogInUser("1", userDataSet);
        WebSocketImpl websocket = spy(new WebSocketImpl(false));
        MessageSystemImpl msg = mock(MessageSystemImpl.class);
        when(msg.getAddressByName("GameMechanic")).thenReturn(new Address());
        websocket.setMS(msg);
        when(websocket.isNotConnected()).thenReturn(false);
        String time = UserDataImpl.getStartServerTime();
        websocket.onWebSocketText("{\"sessionId\":\"null\", \"startServerTime\":\"" + time + "LALALLA" + "\", \"from_x\":\"-1\", \"from_y\":\"-1\", \"to_x\":\"-1\", \"to_y\":\"-1\", \"status\":\"1\"}");
        verify(websocket).onWebSocketText("{\"sessionId\":\"null\", \"startServerTime\":\"" + time + "LALALLA" + "\", \"from_x\":\"-1\", \"from_y\":\"-1\", \"to_x\":\"-1\", \"to_y\":\"-1\", \"status\":\"1\"}");
    }
    @Test
     public void testonWebSocketConstrucktor() {
        WebSocketImpl websocket = spy(new WebSocketImpl(true));
        when(websocket.isNotConnected()).thenReturn(true);
        WebSocket newWebSocket = spy(new WebSocketImpl());
        websocket.onWebSocketText("{\"sessionId\":\"1\", \"startServerTime\":\"" + UserDataImpl.getStartServerTime() + "\", \"from_x\":\"1\", \"from_y\":\"1\", \"to_x\":\"5\", \"to_y\":\"5\", \"status\":\"1\"}");
    }
    @Test
    public void testUpdateUsersColorWhite() {
        WebSocketImpl webSocket = new WebSocketImpl(false);
        Map<String, String> test = new HashMap();
        test.put("black", "white");
        UserDataSet UDS = new UserDataSet();
        UDS.setColor("WHITETESTFORCOLORS");
        UserDataImpl.putLogInUser("black", UDS);
        webSocket.updateUsersColor(test);

        Map<String, String> badTest = new HashMap();
        badTest.put("badColor", "veryBad");
        UserDataSet newUDS = new UserDataSet();
        UserDataImpl.putLogInUser("black", newUDS);
        webSocket.updateUsersColor(badTest);
    }

    @Test
    public void testUpdateUsersColorBlack() {
        WebSocketImpl webSocket = new WebSocketImpl(false);
        Map<String, String> test = new HashMap();
        test.put("white", "black");
        UserDataSet UDS = new UserDataSet();
        UDS.setColor("BLACKTESTFORCOLORS");
        UserDataImpl.putLogInUser("white", UDS);
        webSocket.addNewWS("white");
        webSocket.updateUsersColor(test);
    }
    @Test
    public void testsendStroke() {
        WebSocketImpl webSocket = new WebSocketImpl(false);
        Map<Integer, Stroke> test = new HashMap();
        test.put(0, new Stroke());
        UserDataSet UDS = new UserDataSet();
        UDS.setColor("REDTEST");
        UserDataImpl.putLogInUser("1", UDS);
        WebSocketImpl WSI = spy(new WebSocketImpl(false));
        when(WSI.getSession()).thenReturn(mock(WebSocketSession.class));
        WSI.setMS(new MessageSystemImpl());
        UserDataImpl.putSessionIdAndWS("1", WSI);
        webSocket.sendStroke(test);
    }
    @Test
    public void testAddNewWSNotEmpty() throws Exception {
        UserDataSet userDataSet = mock(UserDataSet.class);
        UserDataImpl.putLogInUser("SessionId", userDataSet);
        WebSocketImpl webSocket = new WebSocketImpl(false);
        webSocket.addNewWS("SessionId");
    }

}
