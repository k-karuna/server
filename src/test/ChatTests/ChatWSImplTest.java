package ChatTests;

import chat.ChatWSImpl;
import frontend.UserDataImpl;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

/**
 * Created by velikolepnii on 08.04.14.
 */
public class ChatWSImplTest {
    private ChatWSImpl ChatWS;
    private String msg = "TESTTEST";


    @Test
    public void testTextNullConnected(){
        ChatWS = new ChatWSImpl();
        ChatWS.onWebSocketText(msg);
    }

    @Test
    public void testTextFailJSON(){
        ChatWSImpl chatWSSpy = spy(new ChatWSImpl());
        when(chatWSSpy.isNotConnected()).thenReturn(false);
        chatWSSpy.onWebSocketText(msg);
    }

    @Test
    public void testTextCorrectJSON(){

        ChatWSImpl chatWSSpy = spy(new ChatWSImpl());
        when(chatWSSpy.isNotConnected()).thenReturn(false);

        String serverTime = UserDataImpl.getStartServerTime();
        chatWSSpy.onWebSocketText("{\"sessionId\":\"1\", \"startServerTime\":\"" +serverTime + "\", \"text\":\"test msg\"}");
    }

    @Test
    public void testTextNullJSON(){
        ChatWSImpl chatWSSpy = spy(new ChatWSImpl());
        when(chatWSSpy.isNotConnected()).thenReturn(false);
        chatWSSpy.onWebSocketText("{\"sessionId\":\"null\", \"startServerTime\":\"null\", \"text\":\"\"}");
    }

    @Test
    public void testTextAddChater(){
        ChatWSImpl chatWSSpy = spy(new ChatWSImpl());
        when(chatWSSpy.isNotConnected()).thenReturn(false);
        String serverTime = UserDataImpl.getStartServerTime();
        chatWSSpy.onWebSocketText("{\"sessionId\":\"1\", \"startServerTime\":\"" +serverTime + "\"}");
    }


    @Test
    public void testTextUnCorrectTime(){

        ChatWSImpl chatWSSpy = spy(new ChatWSImpl());
        when(chatWSSpy.isNotConnected()).thenReturn(false);

        chatWSSpy.onWebSocketText("{\"sessionId\":\"1\", \"startServerTime\":\"123\", \"text\":\"null\"}");
    }

    @Test
    public void testTextJsonNull(){
        ChatWSImpl chatWSSpy = spy(new ChatWSImpl());
        when(chatWSSpy.isNotConnected()).thenReturn(false);
        chatWSSpy.onWebSocketText("{\"sessionId\":\"null\", \"startServerTime\":\"null\", \"text\":\"null\"}");
    }
}
