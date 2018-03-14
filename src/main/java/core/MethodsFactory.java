package core;

import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.events.TestCaseEvent;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static core.Constants.*;

public class MethodsFactory {

    static ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    WebDriver driver;
    private Logger logger = LoggerFactory.getLogger("WebDriver");

    protected static WebDriver driver(){
        return DRIVER.get();
    }

    protected void type(By locator, String s){
        logger.info("Typing by '" + locator + "' <" + s + ">...");
        waitForElementVisibility(locator);
        logger.info("   Clear field" );
        driver().findElement(locator).clear();
        logger.info("   Send keys");
        driver().findElement(locator).sendKeys(s);
    }

    public String getCurrentUrl(){
        waitForPageToBeLoaded();
        return driver().getCurrentUrl();
    }

    private void scrollToElement(By by){
        logger.info("   Scroll to element '" + by + "'");
        ((JavascriptExecutor)driver()).executeScript("arguments[0].scrollIntoView();", driver().findElement(by));
    }
    private void scrollToElement(WebElement element){
        logger.info("   Scroll to element '" + element + "'");
        ((JavascriptExecutor)driver()).executeScript("arguments[0].scrollIntoView();", element);
    }

    protected void click(By by){
        logger.info("Clicking on locator '" + by + "'...");
        waitForElementPresence(by);
        scrollToElement(by);
        logger.info("   Click - " + by);
        driver().findElement(by).click();
    }

    protected void clickWithoutScroll(By by){
        logger.info("Clicking on locator '" + by + "'...");
        waitForElementPresence(by);
        logger.info("   Click - " + by);
        driver().findElement(by).click();
    }

    protected void click(WebElement element){
        logger.info("Clicking on locator '" + element + "'...");
        scrollToElement(element);
        logger.info("   Click - " + element);
        element.click();
    }

    @Step("Get URL: {0}")
    protected void get(String url){
        logger.info("Get URL '" + url + "'");
        driver().get(url);
        waitForPageToBeLoaded();
    }

    @Step("Refresh page")
    protected void refreshPage(){
        logger.info("Refresh page");
        driver().navigate().refresh();
        waitForPageToBeLoaded();
    }

    protected String getAttributeOfElement(By locator, String attrName){
        logger.info("   Get attribute '" + attrName + "' at locator '" + locator + "'");
        return driver().findElement(locator).getAttribute(attrName);
    }

    protected String getTextFromWebElement(WebElement element){
        logger.info("   Get text at element '" + element + "'");
        return element.getText();
    }

    protected void waitForElementVisibility(By by){
        WebDriverWait wait = new WebDriverWait(driver(), 10);
        logger.info("   Wait for element visibility at locator '" + by + "'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> getAllElements(By by){
        logger.info("Getting all elements by locator '" + by + "'");
        return driver().findElements(by);
    }

    protected void waitForElementDisappear(By by, int timeout) {
        logger.info("Wait for element by locator '" + by + "' disappears with timeout " + timeout + " millis");
        long start = Calendar.getInstance().getTimeInMillis();
        long finish = start + timeout * 1000;
        while (start < finish){
            if (driver().findElements(by).size() == 0) {
                return;
            }
            start = Calendar.getInstance().getTimeInMillis();
        }
        throw new TimeoutException("Element " + by + " didn't disappear during " + timeout + " milliseconds!");
    }

    protected void waitForElementPresence(By by){
        logger.info("   Wait for presence of element by locator '" + by + "'");
        WebDriverWait wait = new WebDriverWait(driver(), IMPLICITLY_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForElementPresence(By by, int timeout){
        logger.info("   Wait for presence of element by locator '" + by + "' with timeout " + timeout + "sec.");
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForUrlContains(String text, int timeout){
        logger.info("Wait for URL contains '" + text + "' with timeout" + timeout + "sec.");
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        wait.until(ExpectedConditions.urlContains(text));
    }

    protected void waitForPageToBeLoaded(){
        WebDriverWait wait = new WebDriverWait(driver(), 30);
        logger.info("   Wait for page to be loaded (ReadyState=complete)");
        try {
            wait.until(pageLoaded());
            return;
        } catch (TimeoutException e) {
            logger.info("----------- <<< ERROR: PAGE WASN'T LOADED AFTER " + 30 + " SECONDS !!! >>> -----------");
            driver().navigate().refresh();
        }
        wait = new WebDriverWait(driver(), PAGE_LOAD_TIMEOUT);
        try {
            wait.until(pageLoaded());
        } catch (TimeoutException e) {
            logger.info("----------- <<< ERROR: PAGE WASN'T LOADED AFTER " + PAGE_LOAD_TIMEOUT + " SECONDS !!! >>> -----------");
            e.printStackTrace();
        }
    }

    private static ExpectedCondition<Boolean> pageLoaded(){
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return ((JavascriptExecutor) driver()).executeScript("return document.readyState").equals("complete");
            }

            @Override
            public String toString() {
                return "Page is still loading...";
            }
        };
    }

    protected boolean elementIsPresent(By by){
        logger.info("   Verify if element at '" + by + "' is present");
        return driver().findElements(by).size() > 0;
    }

    protected boolean elementIsVisible(By by){
        logger.info("   Verify if element at '" + by + "' is visible");
        return driver().findElement(by).isDisplayed();
    }

    protected int getAmountOfElements(By by){
        logger.info("Get amount of elements located by '" + by + "'");
        waitForPageToBeLoaded();
        return driver().findElements(by).size();
    }

    protected boolean isChecked(By by){
        logger.info("Verify if checkbox at '" + by + "' is checked");
        return driver().findElement(by).getAttribute("checked") != null;
    }

    protected void switchToOtherTabByIndex(int index){
        logger.info("   Switch to tab with index " + index);
        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver().switchTo().window(tabs.get(index));
    }

    protected List<String> getAllBrowserTabs(){
        logger.info("   Get all browser tabs");
        return new ArrayList<> (driver.getWindowHandles());
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] pngAttachment(String fileName){
        Robot bot = null;
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        bot.mouseMove(0, 0);
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver());
        File scrFile = new File(fileName + ".png");
        try {
            ImageIO.write(screenshot.getImage(), "png", scrFile);
            byte[] bites = Files.readAllBytes(Paths.get(scrFile.getPath()));
            scrFile.delete();
            return bites;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static void printBrowserLog(boolean withWarnings) {
        LogEntries logEntries = driver().manage().logs().get(LogType.BROWSER);
        JavascriptExecutor js = (JavascriptExecutor)driver();
        for (LogEntry entry : logEntries) {
            if (!withWarnings) {
                if (entry.getLevel().getName().equals("SEVERE")) {
                    String message = "CONSOLE: " + new Date(entry.getTimestamp()) + " " + entry.getLevel().getName() + " " + entry.getMessage();
                    System.out.println(message);
                    js.executeScript("console.clear();");
                }
            }else
                System.out.println("CONSOLE: " + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
        js.executeScript("console.clear();");
    }

    static List<String> getBrowserLog(boolean withWarnings) {
        LogEntries logEntries = driver().manage().logs().get(LogType.BROWSER);
        List<String> logs = new ArrayList<>();
        for (LogEntry entry : logEntries) {
            if (!withWarnings){
                if (entry.getLevel().getName().contains("SEVERE")) {
                    logs.add(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
                }
            }
            else {
                logs.add(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            }
        }
        JavascriptExecutor js = (JavascriptExecutor)driver();
        js.executeScript("console.clear();");
        return logs;
    }

    public static void allureSetStory(String story) {
        Allure.LIFECYCLE.fire((TestCaseEvent) testCaseResult -> testCaseResult.getLabels().add(AllureModelUtils.createStoryLabel(story)));
    }
}
