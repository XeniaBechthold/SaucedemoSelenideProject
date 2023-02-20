package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public abstract class AbstractHeaderPage extends BasePage{

    private final SelenideElement basketCounter = $x("//span[@class = 'shopping_cart_badge']");
    private final SelenideElement basket = $x("//div[@id='shopping_cart_container']/a");
    private final SelenideElement burgerMenuButton = $x("//button[@id='react-burger-menu-btn']");
    private final ElementsCollection footerLinks = $$x ("//footer/ul[@class='social']/li/a");


    @Step ("Open basket")
    public BasketPage openBasket(){
        basket.shouldBe(Condition.visible).click();
        return new BasketPage();
    }



    @Step("Check products counter")
    public AbstractHeaderPage checkCounter() {
        basketCounter.shouldBe(Condition.visible);
        return this;
    }

    @Step("Check products counter")
    public AbstractHeaderPage checkCounter(int count) {
        basketCounter.shouldBe(Condition.visible);
        basketCounter.shouldHave(Condition.exactText(String.valueOf(count)));
        return this;
    }

    @Step("Check empty basket")
    public AbstractHeaderPage checkEmptyBasket() {
        basketCounter.shouldBe(Condition.not(Condition.visible));
        return this;
    }

    @Step ("Check general elements on page")
    public AbstractHeaderPage checkGeneralElements() {
        basket.shouldBe(Condition.visible);
        burgerMenuButton.shouldBe(Condition.visible);
        return this;
    }

    @Step ("Check footer links")
    public AbstractHeaderPage checkFooterLinks() {
        for (int i = 0; i < footerLinks.size(); i++) {
            String link = footerLinks.get(i).getAttribute("href");
            footerLinks.get(i).click();
            switchTo().window(1);
            checkUrl(link);
            closeWindow();
            switchTo().window(0);
        }
        return this;
    }

    @Step ("Open burger menu")
    public AbstractHeaderPage openMenu() {
        burgerMenuButton.shouldBe(Condition.visible).click();
        return this;
    }
}
