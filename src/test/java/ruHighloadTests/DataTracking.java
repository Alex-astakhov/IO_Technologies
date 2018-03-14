package ruHighloadTests;

import core.Assertions;
import core.BrowserFactory;
import listeners.ListenerWithBrowserShot;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.ruhighload.RuHighLoadMain;
import ru.yandex.qatools.allure.annotations.Features;

@Listeners({ListenerWithBrowserShot.class})
@Features("Desktop check of data tracking (Task 5)")
public class DataTracking extends BrowserFactory {
    private RuHighLoadMain ruHighLoadMain = new RuHighLoadMain();

    private String targetMethod = "GET";
    private String targetUrl = "https://tt.onthe.io/?k[]=28:pageviews";
    private int articlesToCheck = 3;

    @Test(dataProvider = "targetArticles")
    public void verifyPageViewsTracking(String articleUrl){
        get(articleUrl);
        waitForPageToBeLoaded();
        Assertions.AssertPerformanceLogEntry(getPerformanceLog(), targetMethod, targetUrl);
    }

    @DataProvider
    private Object[] targetArticles(){
        get("https://ruhighload.com/");
        return ruHighLoadMain.getArticlesUrlsList(articlesToCheck).toArray();
    }

}
