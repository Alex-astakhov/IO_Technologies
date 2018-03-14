package pageObjects.reportPages;

import core.MethodsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends MethodsFactory {
    private String authorNameXpath = ".//*[@data-type='author']//span";
    private By authorsName = By.xpath(authorNameXpath);
    private By activeFilter = By.cssSelector("[data-type='author'].on .checkbox");

    private By articleListArticleTitle = By.cssSelector(".data_list_pubs .title");
    private By articlesListAuthor = By.cssSelector(".data_list_pubs [data-tooltip='Author']");

    @Step("Get authors list")
    public List<String> getAuthorsList(){
        List<String> result = new ArrayList<>();
        for (WebElement author: getAllElements(authorsName)) {
            result.add(getTextFromWebElement(author));
        }
        return result;
    }

    @Step("Click on author by name: {0}")
    public void clickOnAuthorByName(String name){
        click(By.xpath(authorNameXpath + "[contains(text(),'" + name + "')]"));
    }

    @Step("Clear author filter")
    public void clearAuthorFilter(){
        if (elementIsPresent(activeFilter))
            click(activeFilter);
    }

    @Step("Get article's author name by index")
    public String getArticleAuthorNameByIndex(int index){
        return getTextFromWebElement(getAllElements(articlesListAuthor).get(index));
    }

    @Step("Get count of articles")
    public int getCountOfArticles(){
        return getAmountOfElements(articleListArticleTitle);
    }
}
