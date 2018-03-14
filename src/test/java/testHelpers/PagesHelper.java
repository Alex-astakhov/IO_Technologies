package testHelpers;

import core.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import ru.yandex.qatools.allure.annotations.Step;

public class PagesHelper extends BrowserFactory {
    private By loader = By.cssSelector(".loader_overlay.on");

    @Step("Wait until loader disappears")
    public void waitForLoaderIsOff(){
        try{
            waitForElementPresence(loader, 2);
            waitForElementDisappear(loader, 20);
            waitForPageToBeLoaded();
        } catch (TimeoutException e){
            System.out.println("No loader...");
        }

    }

    @Step("Get page title")
    public String getPageTitle(){
        return driver().getTitle();
    }
}
