package core;

import browsers.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserFactory extends MethodsFactory {
    public static String BROWSER;

    @BeforeTest
    @Parameters({"browser"})
    public void setupUp(@Optional("CH") String browser) {
        switch (browser){
            case "CH": driver = new MyChrome().getDriver(); BROWSER = "Google Chrome"; break;
            case "MCH": driver = new MyChromeMobile().getDriver(); BROWSER = "Google Chrome Mobile"; break;

        }
        DRIVER.set(driver);
        }

    @AfterTest
    public void tearDown() {
        try {
            driver().close();
            driver().quit();
        }catch (Exception e){
            System.out.println("Driver error preventing from Quitting.");
        }
    }

    private List<String> getErrors(){
        List<String> list = MethodsFactory.getBrowserLog(false);
        if (list.isEmpty())
            return list;
        List<String> errors = new ArrayList<>();
        for (String s: list) {
            if (s.contains(".css") || s.contains(".js"))
                errors.add(s);
        }
        return errors;
    }

    protected List<String> getPerformanceLog(){
        LogEntries logs = driver().manage().logs().get(LogType.PERFORMANCE);
        List<String> list = new ArrayList<>();
        for (LogEntry entry: logs) {
            list.add(entry.getMessage());
        }
        return list;
    }

    protected void setCookie(Cookie cookie) {
        driver().manage().addCookie(cookie);
    }

    protected void clearCookies(){
        driver().manage().deleteAllCookies();
    }
}
