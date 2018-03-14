package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.logging.Level;

public class MyChrome {

    @Step("Start CHROME")
    public WebDriver getDriver(){
        System.out.println("Start CHROME");
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        dc.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(dc);
    }
}
