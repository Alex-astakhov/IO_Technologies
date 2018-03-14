package pageObjects.reportPages;

import core.MethodsFactory;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

public class LeftMenuBar extends MethodsFactory {
    private By tvDashboardIcon = By.cssSelector(".fullscreen_icon");
    private By homeLeftBar = By.cssSelector("[qa-id='home']");
    private By articlesLeftBar = By.cssSelector("[qa-id='articles']");
    private By authorsLeftBar = By.cssSelector("[qa-id='authors']");

    @Step("Left menu bar: Click on 'TV Dashboard' icon")
    public void clickTvDashboard(){
        click(tvDashboardIcon);
    }

    @Step("Left menu bar: Click on 'Home' option")
    public void clickHome(){
        click(homeLeftBar);
    }

    @Step("Left menu bar: Click on 'Articles' option")
    public void clickArticles(){
        click(articlesLeftBar);
    }

    @Step("Left menu bar: Click on 'Authors' option")
    public void clickAuthors(){
        click(authorsLeftBar);
    }
}
