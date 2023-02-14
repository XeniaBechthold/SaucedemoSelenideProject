import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage{
    private final SelenideElement loginInput = $x("//input[@id='user-name']");
    private final SelenideElement passwordInput = $x("//input[@id='password']");
    private final SelenideElement loginButton = $x("//input[@id='login-button']");
    private final SelenideElement robotLogo = $x("//div[@class='bot_column']");
    private final SelenideElement loginLogo = $x("//div[@class='login_logo']");
    private final SelenideElement errorMessage = $x("//div[contains(@class,'error-message-container')]");

    @Step ("Login with username '{username}' and password '{password}'")
    public HomePage login (String username, String password) {
        loginInput.shouldBe(Condition.visible).sendKeys(username);
        passwordInput.shouldBe(Condition.visible).sendKeys(password);
        loginButton.shouldBe(Condition.visible).click();
        if(username.equals("locked_out_user")){
            errorMessage.shouldHave(Condition.cssClass("error"));}
        return new HomePage();
    }

    @Step ("Check elements on the page")
    public LoginPage checkElements(){
        loginButton.shouldBe(Condition.visible);
        loginInput.shouldBe(Condition.visible);
        passwordInput.shouldBe(Condition.visible);
        loginLogo.shouldBe(Condition.visible);
        robotLogo.shouldBe(Condition.visible);
        return this;
    }

    @Step ("Check page url")
    @Override
    public LoginPage checkUrl(String url) {
        return (LoginPage) super.checkUrl(url);
    }

}
