package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import product.Product;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

public class BasketPage extends AbstractHeaderPage{

    private final SelenideElement title = $x("//span[@class='title']");

    private final ElementsCollection products = $$x("//div[@class='cart_item']");
    private final SelenideElement checkout = $x("//button[@id='checkout']");
    private final SelenideElement firstNameInput = $x("//input[@id='first-name']");
    private final SelenideElement lastNameInput = $x("//input[@id='last-name']");
    private final SelenideElement zipInput = $x("//input[@id='postal-code']");
    private final SelenideElement continueButton = $x("//input[@id='continue']");
    private final SelenideElement continueShopping = $x("//button[@id='continue-shopping']");
    private final SelenideElement finishOrder = $x("//button[@id='finish']");
    private final SelenideElement backHome = $x("//button[@id='back-to-products']");
    private final SelenideElement subTotal = $x("//div[@class='summary_subtotal_label']");
    private final SelenideElement errorMessage = $x("//div[@class='error-message-container error']");
    private final SelenideElement finishMessage = $x("//h2[@class='complete-header']");

    @Step ("Check basket page opening")
    public BasketPage checkPage() {
        title.shouldBe(Condition.visible);
        return this;
    }

    @Step ("Check product list size")
    public BasketPage checkProductListSize(int count) {
       products.shouldHave(CollectionCondition.size(count));
        return this;
    }

    @Step ("Check product parameters")
    public BasketPage checkProduct(Product product) {
       SelenideElement card = products.find(Condition.text(product.getName()));

       /*card.shouldHave(Condition.text(product.getDescription()));
       card.shouldHave(Condition.text(String.valueOf(product.getPrice())));*/


        card.$x(".//div[@class='inventory_item_desc']").shouldHave(Condition.exactText(product.getDescription()));
        card.$x(".//div[@class='inventory_item_price']").shouldHave(Condition.exactText("$" + product.getPrice()));
       return this;
    }

    @Step ("Check product parameters")
    public BasketPage checkTotalProductPrice(double value) {
        subTotal.shouldHave(Condition.text(String.valueOf(value)));
        return this;
    }

    @Step ("Check field validation")
    public BasketPage checkFieldValidation() {
        checkout.shouldBe(Condition.visible).click();
        continueButton.click();
        errorMessage.shouldBe(Condition.visible);
        return this;
    }


    @Step ("Form order")
    public BasketPage orderForm(String firstName, String lastName, String zip) {
        checkout.shouldBe(Condition.visible).click();
        firstNameInput.shouldBe(Condition.visible).sendKeys(firstName);
        lastNameInput.shouldBe(Condition.visible).sendKeys(lastName);
        zipInput.shouldBe(Condition.visible).sendKeys(zip);
        continueButton.click();
        return this;
    }

    @Step ("Remove from Basket")
    public BasketPage removeFromBasket() {
        ElementsCollection buttons = $$x(".//button[contains(@id, 'remove')]");
        buttons.get(new Random().nextInt(buttons.size())).shouldBe(Condition.visible).click();
        return this;
    }

    @Step ("Return to home page")
    public HomePage returnToHomePage() {
        continueShopping.shouldBe(Condition.visible).click();
        return new HomePage();
    }

    @Step ("Finish order")
    public BasketPage finishOrder() {
        finishOrder.shouldBe(Condition.visible).click();
        finishMessage.shouldBe(Condition.visible).shouldHave(Condition.exactText("THANK YOU FOR YOUR ORDER"));
        return this;
    }

    @Step ("Back to home page after order")
    public HomePage backToHomePageAfterOrder() {
        backHome.shouldBe(Condition.visible).click();
        return new HomePage();
    }






}
