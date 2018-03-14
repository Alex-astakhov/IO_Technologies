package reportingTests;

import core.Assertions;
import core.BrowserFactory;
import core.Constants;
import listeners.ListenerWithBrowserShot;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.reportPages.LeftMenuBar;
import ru.yandex.qatools.allure.annotations.Features;
import testHelpers.AuthHelper;
import testHelpers.PagesHelper;


@Listeners({ListenerWithBrowserShot.class})
@Features("Desktop check left menu bar (Task 1)")
public class LeftMenuBarTest extends BrowserFactory {
    private LeftMenuBar leftMenuBar = new LeftMenuBar();
    private PagesHelper pagesHelper = new PagesHelper();
    private AuthHelper authHelper = new AuthHelper();

    @BeforeClass
    public void login(){
        authHelper.loginUser(Constants.EMAIL, Constants.PASSWORD);
        authHelper.openProjectPageByProjectIndex(0);
    }

    @BeforeMethod
    public void chooseFirstTab(){
        if (getAllBrowserTabs().size() > 1){
            switchToOtherTabByIndex(0);
        }
    }

    @Test
    public void verifyArticlesOption(){
        leftMenuBar.clickArticles();
        waitForPageToBeLoaded();
        String currentPageTitle = pagesHelper.getPageTitle();
        Assert.assertTrue(currentPageTitle.contains("Articles"), "Wrong content is shown!\n " +
                "Actual page title: " + currentPageTitle);
    }

    @Test
    public void verifyAuthorsOption(){
        leftMenuBar.clickAuthors();
        waitForPageToBeLoaded();
        String currentPageTitle = pagesHelper.getPageTitle();
        Assert.assertTrue(currentPageTitle.contains("Authors"), "Wrong content is shown!\n " +
                "Actual page title: " + currentPageTitle);
    }

    @Test
    public void verifyHomeOption(){
        leftMenuBar.clickHome();
        waitForPageToBeLoaded();
        String currentPageTitle = pagesHelper.getPageTitle();
        Assert.assertTrue(currentPageTitle.contains("Home"), "Wrong content is shown!\n " +
                "Actual page title: " + currentPageTitle);
    }

    @Test
    public void verifyTvDashboardOption(){
        leftMenuBar.clickTvDashboard();
        switchToOtherTabByIndex(1);
        waitForPageToBeLoaded();
        Assertions.urlContains("dash.onthe.io", 0,"Wrong URL!\nActual URL: " + getCurrentUrl());
    }

    @AfterClass
    public void logout(){
        if (getAllBrowserTabs().size() > 1){
            driver().close();
            switchToOtherTabByIndex(0);
        }
        clearCookies();
    }
}
