package pageObjects.reportPages;

import core.MethodsFactory;
import enums.TimeFrames;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

public class TimeFramesBar extends MethodsFactory {
    private String timeFrameLocator = ".period_switch_container [data-period='";
    private By activeTimeFrame = By.cssSelector(".period_switch_container .period_switch_item.on");

    @Step("Choose time frame - {0}")
    public void chooseTimeFrame(TimeFrames timeFilter){
        String currentLocator = timeFrameLocator + timeFilter.toString().toLowerCase() + "']";
        click(By.cssSelector(currentLocator));
    }

    @Step("Get active time frame")
    public TimeFrames getActiveTimeFrame(){
        String activeFrameName = getAttributeOfElement(activeTimeFrame, "data-period");
        return Enum.valueOf(TimeFrames.class, activeFrameName.toUpperCase());
    }

}
