package DBTests;

import dbService.UserDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TimeHelper;

import java.util.Random;

/**
 * Created by velikolepnii on 10.04.14.
 */
public class UserDataSetTest {

    private boolean isValuesInDelta(long value1, long value2, long delta) {
        return Math.abs(value1 - value2) <= delta;
    }

    final long visitDelta = 1; //in seconds

    @Test
    public void TestDefaultConstructor() {
        UserDataSet userDataSet = new UserDataSet();

        //default constructor
        Assert.assertEquals(userDataSet.getId(), 0); //by default = 0
        Assert.assertEquals(userDataSet.getPostStatus(), 0); //by default = 0
        Assert.assertEquals(userDataSet.getNick(), ""); //by default = ""
        Assert.assertEquals(userDataSet.getColor(), null); //by default = null
        Assert.assertTrue(isValuesInDelta(userDataSet.getLastVisit(), TimeHelper.getCurrentTime(), visitDelta));
        Assert.assertEquals(userDataSet.getRating(), 0); //by default = null
        Assert.assertEquals(userDataSet.getWinQuantity(), 0); //by default = null
        Assert.assertEquals(userDataSet.getLoseQuantity(), 0); //by default = null
    }

    @Test
    public void TestConstructor() {
        Random rand = new Random();
        int id = rand.nextInt();
        int rating = rand.nextInt();
        int winQuantity = rand.nextInt();
        int loseQuantity = rand.nextInt();
        String nick = "MyNickName";

        UserDataSet userDataSet = new UserDataSet(id, nick, rating, winQuantity, loseQuantity);
        Assert.assertEquals(userDataSet.getId(), id);
        Assert.assertEquals(userDataSet.getPostStatus(), 0); //by default = 0
        Assert.assertEquals(userDataSet.getNick(), nick);
        Assert.assertEquals(userDataSet.getColor(), null); //by default = null
        Assert.assertTrue(isValuesInDelta(userDataSet.getLastVisit(), TimeHelper.getCurrentTime(), visitDelta));
        Assert.assertEquals(userDataSet.getRating(), rating);
        Assert.assertEquals(userDataSet.getWinQuantity(), winQuantity);
        Assert.assertEquals(userDataSet.getLoseQuantity(), loseQuantity);
    }

    @Test
    public void TestMakeLike() {
        Random rand = new Random();
        int id = rand.nextInt();
        int rating = rand.nextInt();
        int winQuantity = rand.nextInt();
        int loseQuantity = rand.nextInt();
        String nick = "MyNickName";

        UserDataSet userDataSetBase = new UserDataSet(id, nick, rating, winQuantity, loseQuantity);
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.makeLike(userDataSetBase);
        Assert.assertEquals(userDataSet.getId(), id);
        Assert.assertEquals(userDataSet.getPostStatus(), 0);
        Assert.assertEquals(userDataSet.getNick(), nick);
        Assert.assertEquals(userDataSet.getColor(), null);
        Assert.assertTrue(isValuesInDelta(userDataSet.getLastVisit(), TimeHelper.getCurrentTime(), visitDelta));
        Assert.assertEquals(userDataSet.getRating(), rating);
        Assert.assertEquals(userDataSet.getWinQuantity(), winQuantity);
        Assert.assertEquals(userDataSet.getLoseQuantity(), loseQuantity);
    }

    @Test
    public void otherFunctional() {
        UserDataSet userDataSet = new UserDataSet();
        Assert.assertTrue(isValuesInDelta(userDataSet.getLastVisit(), TimeHelper.getCurrentTime(), visitDelta));
        int postStatus = 7;
        userDataSet.setPostStatus(postStatus);
        Assert.assertEquals(userDataSet.getPostStatus(), postStatus);

        String color = "Some color";
        userDataSet.setColor(color);
        Assert.assertEquals(userDataSet.getColor(), color);

        userDataSet.visit();
        Assert.assertTrue(isValuesInDelta(userDataSet.getLastVisit(), TimeHelper.getCurrentTime(), visitDelta));

        final int winDiff = 5;
        userDataSet.win(winDiff);
        Assert.assertEquals(userDataSet.getWinQuantity(), 1);
        Assert.assertEquals(userDataSet.getRating(), winDiff);
        final int loseDiff = 3;
        userDataSet.lose(loseDiff);
        Assert.assertEquals(userDataSet.getLoseQuantity(), 1);
        Assert.assertEquals(userDataSet.getRating(), winDiff - loseDiff);
    }
}
