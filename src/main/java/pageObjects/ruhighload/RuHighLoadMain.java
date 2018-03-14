package pageObjects.ruhighload;

import core.MethodsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

public class RuHighLoadMain extends MethodsFactory {
    private By articleLink = By.cssSelector("#content li a");

    @Step("Get all articles URLs")
    public List<String> getArticlesUrlsList(int maxCount){
        List<String> result = new ArrayList<>();
        List<WebElement> allArticles = getAllElements(articleLink);
        for (int i = 0; i < maxCount && i < allArticles.size(); i++) {
            result.add(allArticles.get(i).getAttribute("href"));
        }
        return result;
    }
}
