package reportingTests;

import core.BrowserFactory;
import core.Constants;
import enums.TimeFrames;
import listeners.ListenerWithBrowserShot;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.reportPages.TimeFramesBar;
import ru.yandex.qatools.allure.annotations.Features;
import testHelpers.AuthHelper;
import testHelpers.PagesHelper;

@Listeners({ListenerWithBrowserShot.class})
@Features("Desktop check of time frames switching (Task 2)")
public class TimeFramesTest extends BrowserFactory {
    private TimeFramesBar timeFramesBar = new TimeFramesBar();
    private AuthHelper authHelper = new AuthHelper();
    private PagesHelper pagesHelper = new PagesHelper();

    @BeforeClass
    public void login(){
        authHelper.loginUser(Constants.EMAIL, Constants.PASSWORD);
        authHelper.openProjectPageByProjectIndex(0);
        pagesHelper.waitForLoaderIsOff();
    }

    @Test(dataProvider = "timeFramesToCheck")
    public void verifyTimeFramesSwitch(TimeFrames frame){
        timeFramesBar.chooseTimeFrame(frame);
        pagesHelper.waitForLoaderIsOff();
        Assert.assertEquals(timeFramesBar.getActiveTimeFrame(), frame,
                "Active time frame isn't match to expected!");
    }

    @DataProvider
    private Object[] timeFramesToCheck(){
        return TimeFrames.values();
    }

    @AfterClass
    public void logout(){
        clearCookies();
    }
}