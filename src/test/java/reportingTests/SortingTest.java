package reportingTests;

import core.Assertions;
import core.BrowserFactory;
import core.Constants;
import dataModels.ArticleModel;
import enums.ArticleMetrics;
import enums.TimeFrames;
import listeners.ListenerWithBrowserShot;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;
import pageObjects.reportPages.ArticlesPage;
import pageObjects.reportPages.LeftMenuBar;
import pageObjects.reportPages.TimeFramesBar;
import ru.yandex.qatools.allure.annotations.Features;
import testHelpers.AuthHelper;
import testHelpers.PagesHelper;

import java.util.List;

@Listeners({ListenerWithBrowserShot.class})
@Features("Desktop check sorting by metrics (Task 4)")
public class SortingTest extends BrowserFactory {
    private AuthHelper authHelper = new AuthHelper();
    private PagesHelper pagesHelper = new PagesHelper();
    private ArticlesPage articlesPage = new ArticlesPage();

    @BeforeClass
    public void login(){
        authHelper.loginUser(Constants.EMAIL, Constants.PASSWORD);
        authHelper.openProjectPageByProjectIndex(0);
        pagesHelper.waitForLoaderIsOff();
        new LeftMenuBar().clickArticles();
        pagesHelper.waitForLoaderIsOff();
        new TimeFramesBar().chooseTimeFrame(TimeFrames.YESTERDAY);
        pagesHelper.waitForLoaderIsOff();
    }

    @Test(dataProvider = "metricsProvider")
    public void verifySortingByMetricsFromBest(ArticleMetrics metric){
        articlesPage.setSortingDirection(true);
        pagesHelper.waitForLoaderIsOff();
        articlesPage.sortByMetric(metric);
        pagesHelper.waitForLoaderIsOff();
        List<ArticleModel> articlesMetricsList = articlesPage.getAllArticlesFromPage();
        Assertions.assertArticlesOrder(articlesMetricsList, metric, true);
        articlesPage.clickNext();
        pagesHelper.waitForLoaderIsOff();
        articlesMetricsList = articlesPage.getAllArticlesFromPage();
        Assertions.assertArticlesOrder(articlesMetricsList, metric, true);
    }

    @Test(dataProvider = "metricsProvider")
    public void verifySortingByMetricsFromWorst(ArticleMetrics metric){
        articlesPage.setSortingDirection(false);
        pagesHelper.waitForLoaderIsOff();
        articlesPage.sortByMetric(metric);
        pagesHelper.waitForLoaderIsOff();
        List<ArticleModel> articlesMetricsList = articlesPage.getAllArticlesFromPage();
        Assertions.assertArticlesOrder(articlesMetricsList, metric, false);
        articlesPage.clickNext();
        pagesHelper.waitForLoaderIsOff();
        articlesMetricsList = articlesPage.getAllArticlesFromPage();
        Assertions.assertArticlesOrder(articlesMetricsList, metric, false);
    }

    @DataProvider
    private Object[] metricsProvider(){
        return ArticleMetrics.values();
    }

    @AfterMethod
    public void backToFirstPage(){
        articlesPage.clickBack();
        pagesHelper.waitForLoaderIsOff();
    }

    @AfterClass
    public void logout(){
        clearCookies();
    }
}
