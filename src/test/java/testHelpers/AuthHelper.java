package testHelpers;

import core.BrowserFactory;
import core.Constants;
import pageObjects.otherPages.LoginPage;
import pageObjects.otherPages.ProjectsPage;

public class AuthHelper extends BrowserFactory{
    private LoginPage loginPage = new LoginPage();

    public void loginUser(String email, String password){
        get(loginPage.PAGE_URL);
        loginPage.typeEmail(email);
        loginPage.typePassword(password);
        loginPage.clickLogin();
        waitForUrlContains("projects", 10);
    }

    public void openProjectPageByProjectIndex(int index){
        new ProjectsPage().clickProjectButtonByIndex(index);
        setCookie(Constants.WIDGET_COOKIE);
        refreshPage();
    }
}
