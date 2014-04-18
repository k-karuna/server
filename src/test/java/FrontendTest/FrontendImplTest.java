package FrontendTest;

import dbService.UserDataSet;
import frontend.FrontendImpl;
import frontend.UserDataImpl;
import messageSystem.MessageSystemImpl;
import org.eclipse.jetty.server.Request;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.SysInfo;

import static org.mockito.Mockito.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by velikolepnii on 13.04.14.
 */
public class FrontendImplTest {
    private HttpServletResponse mockResponse;
    private Request mockBaseRequest;
    private HttpServletRequest mockRequest;
    enum status {nothing,haveCookie,haveCookieAndPost,waiting,ready}
    @BeforeMethod
    public void setUp() {
        mockResponse = mock(HttpServletResponse.class);
        mockBaseRequest = mock(Request.class);
        mockRequest = mock(HttpServletRequest.class);
        SysInfo sysInfo = new SysInfo();
        (new Thread(sysInfo)).start();
    }
    @Test
    public void testonNothingStatus() {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
        frontend.onNothingStatus("target", "1", new UserDataSet(), UserDataImpl.getStartServerTime(), mockResponse);
        frontend.onNothingStatus("/", "2", new UserDataSet(), UserDataImpl.getStartServerTime(), mockResponse);
    }
    @Test
    public void testHandle() {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { new Cookie("sessionId", "sessionIdCookie"), new Cookie("startServerTime", UserDataImpl.getStartServerTime())});
        frontend.handle("/admin", mockBaseRequest, mockRequest, mockResponse);
    }
    @Test
    public void testHandleNegative() {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { new Cookie("sessionId", "sessionIdCookie"), new Cookie("startServerTime", UserDataImpl.getStartServerTime())});
        UserDataImpl.putSessionIdAndUserSession("sessionIdCookie", new UserDataSet());
        when(mockRequest.getMethod()).thenReturn("POST");
        frontend.handle("/admin", mockBaseRequest, mockRequest, mockResponse);
        verify(mockBaseRequest).setHandled(true);
        frontend.handle("NOTINWEB", mockBaseRequest, mockRequest, mockResponse);
        frontend.handle("/img/NOTINWEB", mockBaseRequest, mockRequest, mockResponse);
        when(mockRequest.getMethod()).thenReturn("GET");
        frontend.handle("/game", mockBaseRequest, mockRequest, mockResponse);
        frontend.handle("/rules", mockBaseRequest, mockRequest, mockResponse);
        frontend.handle("/wait", mockBaseRequest, mockRequest, mockResponse);
        UserDataSet mockUDS =  spy(new UserDataSet());
        UserDataImpl.putSessionIdAndUserSession("sessionIdCookie", mockUDS);
        when(mockUDS.getPostStatus()).thenReturn(23);
        frontend.handle("/wait", mockBaseRequest, mockRequest, mockResponse);
        when(mockUDS.getId()).thenReturn(23);
        frontend.handle("/logout", mockBaseRequest, mockRequest, mockResponse);
        when(mockRequest.getMethod()).thenReturn("POST");
        frontend.handle("/wait", mockBaseRequest, mockRequest, mockResponse);
    }
    @Test
    public void testinWeb() {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
        String urls[] = new String[] { "/", "/wait", "/game", "/profile", "/admin", "/rules", "/logout", "/reg" };
        boolean result;
        for(int i = 0; i < urls.length; i++) {
            result = frontend.inWeb(urls[i]);
            Assert.assertTrue(result);
        }
    }
    @Test
    public void testIsStatic() {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
        boolean result;
        result = frontend.isStatic("no"); //first if less 4
        Assert.assertTrue(!result);
        result = frontend.isStatic("FOUR");
        Assert.assertTrue(!result);
        result = frontend.isStatic("/js/");
        Assert.assertTrue(result);
        result = frontend.isStatic("/img/");
        Assert.assertTrue(result);
        result = frontend.isStatic("/css/");
        Assert.assertTrue(result);
        result = frontend.isStatic("/ALLFALSE/");
        Assert.assertTrue(!result);
    }
    @Test
    public void testNewUser() {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
        boolean result;
        result = frontend.newUser(null, null);
        Assert.assertTrue(result);
        result = frontend.newUser("Session", "TIME");
        Assert.assertTrue(result);
        result = frontend.newUser("Session", UserDataImpl.getStartServerTime());
        Assert.assertTrue(result);
        UserDataImpl.putSessionIdAndUserSession("SessionID", new UserDataSet());
        result = frontend.newUser("SessionID", UserDataImpl.getStartServerTime());
        Assert.assertTrue(!result);
        UserDataImpl.putSessionIdAndUserSession("SessionID1", new UserDataSet());
        result = frontend.newUser("SessionID1", "BADTIME");
        Assert.assertTrue(result);
    }

    @Test
    public void testOnReadyStatus () {
        FrontendImpl frontend = new FrontendImpl(new MessageSystemImpl());
    }
}
