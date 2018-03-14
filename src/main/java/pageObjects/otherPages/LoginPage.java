package pageObjects.otherPages;

import core.MethodsFactory;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

public class LoginPage extends MethodsFactory {

    public final String PAGE_URL = "https://onthe.io/auth";
    private By emailInput = By.cssSelector("[name='email']");
    private By passwordInput = By.cssSelector("[name='pwd']");
    private By loginButton = By.cssSelector("#auth button");

    @Step("Login page: Type email - {0}")
    public void typeEmail(String email){
        type(emailInput, email);
    }

    @Step("Login page: Type password - {0}")
    public void typePassword(String password){
        type(passwordInput, password);
    }

    @Step("Login page: Click 'Login' button")
    public void clickLogin(){
        click(loginButton);
    }
}
