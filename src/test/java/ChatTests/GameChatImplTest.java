package ChatTests;

import base.GameChat;
import base.MessageSystem;
import chat.GameChatImpl;
import dbService.UserDataSet;
import frontend.UserDataImpl;
import messageSystem.MessageSystemImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by velikolepnii on 08.04.14.
 */
public class GameChatImplTest {
    private MessageSystem messageSystem;
    private GameChat gameChat;
    private String sessionIdfirst = "872", sessionIdsecond = "36456", sessionIdThirth = "3214";

    @BeforeMethod
    public void setUp() {
        messageSystem = new MessageSystemImpl();
        gameChat = new GameChatImpl(messageSystem);
    }
    @Test
    public void testSendMessageCorrect(){
        gameChat.createChat(sessionIdfirst, sessionIdsecond);
        UserDataImpl.putLogInUser(sessionIdfirst, new UserDataSet());

        GameChatImpl.sendMessage(sessionIdfirst, "TESTTEST");
    }

    @Test
    public void testSendMessageNull(){
        gameChat.createChat(sessionIdfirst, sessionIdsecond);
        UserDataImpl.putLogInUser(sessionIdThirth, new UserDataSet());

        GameChatImpl.sendMessage(sessionIdThirth, "TESTTEST");
    }
}
