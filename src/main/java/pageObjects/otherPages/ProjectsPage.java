package pageObjects.otherPages;

import core.MethodsFactory;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

public class ProjectsPage extends MethodsFactory {
    private By projectButton = By.cssSelector(".projects li");

    @Step("Click projectButton by index {0}")
    public void clickProjectButtonByIndex(int index){
        click(getAllElements(projectButton).get(index));
    }
}
