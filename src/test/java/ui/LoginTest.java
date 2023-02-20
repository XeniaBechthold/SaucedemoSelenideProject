package ui;

import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ui.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    private static final String URL = "https://www.saucedemo.com";

    @DataProvider (name = "getCredentials")
    public Object[][] getCredentials(){
        return new Object[][] {{"standard_user","secret_sauce"},
                                {"problem_user","secret_sauce"},
                                {"performance_glitch_user","secret_sauce"}};
    }

    @BeforeMethod
    public void setUp(){
        open(URL);
    }

    @Test
    @Description("Check Elements")
    public void checkElements(){
        new LoginPage()
                .checkElements();
    }

    @Test
    @Description("Check URL")
    public void checkURL(){
        new LoginPage()
                .checkUrl(URL);
    }

    @Test (dataProvider = "getCredentials")
    @Description("Check Login")
    public void login(String login, String password){
        new LoginPage().login(login, password)
                .checkPage();
    }

    @Test
    @Description("Check Login with wrong credentials")
    public void invalidLogin(){
        new LoginPage().login("locked_out_user", "secret_sauce");
    }


}
