package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverConditions;

public class BasePage {

    public BasePage checkUrl(String url){
        Selenide.webdriver().shouldHave(WebDriverConditions.currentFrameUrlStartingWith(url));
        return this;
    }
}
