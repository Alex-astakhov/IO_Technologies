package reportingTests;

import core.BrowserFactory;
import core.Constants;
import enums.TimeFrames;
import listeners.ListenerWithBrowserShot;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.reportPages.HomePage;
import pageObjects.reportPages.TimeFramesBar;
import ru.yandex.qatools.allure.annotations.Features;
import testHelpers.AuthHelper;
import testHelpers.PagesHelper;

@Listeners({ListenerWithBrowserShot.class})
@Features("Desktop check author filter (Task 3)")
public class AuthorFilter extends BrowserFactory {
    private AuthHelper authHelper = new AuthHelper();
    private HomePage homePage = new HomePage();
    private PagesHelper pagesHelper = new PagesHelper();

    @DataProvider
    private Object[] authors(){
        authHelper.loginUser(Constants.EMAIL, Constants.PASSWORD);
        authHelper.openProjectPageByProjectIndex(0);
        pagesHelper.waitForLoaderIsOff();
        new TimeFramesBar().chooseTimeFrame(TimeFrames.MONTH);
        pagesHelper.waitForLoaderIsOff();
        return homePage.getAuthorsList().toArray();
    }

    @BeforeMethod
    private void clearFilter(){
        homePage.clearAuthorFilter();
        pagesHelper.waitForLoaderIsOff();
    }

    @Test(dataProvider = "authors")
    public void verifyAuthorsFilter(String authorName){
        System.out.println("Check author filter: " + authorName);
        homePage.clickOnAuthorByName(authorName);
        pagesHelper.waitForLoaderIsOff();
        for (int i = 0; i < homePage.getCountOfArticles(); i++) {
            String currentAuthorName = homePage.getArticleAuthorNameByIndex(i);
            Assert.assertEquals(currentAuthorName, authorName, "Wrong author name in line " + ++i);
        }
    }

    @AfterClass
    public void logout(){
        clearCookies();
    }
}
