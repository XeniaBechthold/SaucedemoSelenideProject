import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverConditions;

import static com.codeborne.selenide.WebDriverConditions.url;

public class BasePage {

    public BasePage checkUrl(String url){
        Selenide.webdriver().shouldHave(WebDriverConditions.currentFrameUrlStartingWith(url));
        return this;
    }
}
