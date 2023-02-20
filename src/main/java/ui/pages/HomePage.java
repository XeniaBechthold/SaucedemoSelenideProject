package ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ui.product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

public class HomePage extends AbstractHeaderPage{

    private final SelenideElement pageName = $x("//span[.='Products']");
    private final SelenideElement sort = $x("//span[@class='select_container']");
    private final SelenideElement sortOptions = $x("//select[@class='product_sort_container']");
    private final SelenideElement backToProducts = $x("//button[@id='back-to-products']");

    private final ElementsCollection cards = $$x ("//div[@class='inventory_item']");

    private final ElementsCollection navBarItems = $$x ("//nav/a");



    @Step ("Check correct page is opened")
    public HomePage checkPage() {
        pageName.shouldBe(Condition.visible);
        return this;
    }

    @Override
    @Step ("Check general elements on page")
    public HomePage checkGeneralElements() {
        sort.shouldBe(Condition.visible);
        return (HomePage) super.checkGeneralElements();
    }

    @Step ("Check cards list")
    public HomePage checkCardsList() {
        cards.shouldBe(CollectionCondition.sizeNotEqual(0));
        return this;
    }

    @Step ("Check default sort")
    public HomePage checkDefaultSort() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            names.add(cards.get(i).$x(".//a[contains(@id, 'title_link')]").getText());
        }
        Collections.sort(names);
        cards.shouldBe(CollectionCondition.texts(names));
        return this;
    }

    @Step ("Check reverse sort")
    public HomePage checkReverseSort() {
        sort.click();
        sortOptions.selectOptionByValue("za");
        List<String> names = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            names.add(cards.get(i).$x(".//a[contains(@id, 'title_link')]").getText());
        }
        names.sort(Collections.reverseOrder());
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }
        cards.shouldBe(CollectionCondition.texts(names));
        return this;
    }

    @Step ("Check price default sort")
    public HomePage checkPriceDefaultSort() {
        sort.click();
        sortOptions.selectOptionByValue("lohi");
        List<Double> prices = getListOfPrices();
        Collections.sort(prices);
        List<String> cardsPrices = parsePricesToString(prices);

        cards.shouldBe(CollectionCondition.texts(cardsPrices));
        return this;
    }

    private List<String> parsePricesToString(List<Double> prices) {
        List<String> cardsPrices = new ArrayList<>();
        for (Double price : prices) {
            cardsPrices.add(price + "");
        }
        return cardsPrices;
    }

    private List<Double> getListOfPrices() {
        List<Double> prices = new ArrayList<>();
        for (SelenideElement card : cards) {
            String text = card.$x(".//div[contains(@class, 'inventory_item_price')]").getText();
            prices.add(Double.parseDouble(text.substring(1)));
        }
        return prices;
    }

    @Step ("Check price reverse sort")
    public HomePage checkPriceReverseSort() {
        sort.click();
        sortOptions.selectOptionByValue("hilo");
        List<Double> prices = getListOfPrices();
        prices.sort(Collections.reverseOrder());
        List<String> cardsPrices = parsePricesToString(prices);

        cards.shouldBe(CollectionCondition.texts(cardsPrices));
        return this;
    }


    @Step ("Check cards price")
    public HomePage checkPrice() {
        cards.shouldBe(CollectionCondition.allMatch("Not all elements have price", card ->
             card.findElement(By.xpath(".//div[contains(@class, 'inventory_item_price')]")).isDisplayed()));
        return this;
    }

    @Step ("Check add to card button")
    public HomePage checkButton() {
        cards.shouldBe(CollectionCondition.allMatch("Not all elements have buttons", card ->
                card.findElement(By.xpath(".//button[contains(text(), 'Add to cart')]")).isDisplayed()));
        return this;
    }

    @Step ("Check cards image")
    public HomePage checkImage() {
        cards.shouldBe(CollectionCondition.allMatch("Not all elements have images", card ->
                card.findElement(By.xpath(".//img[@class='inventory_item_img']")).isDisplayed()));
        return this;
    }

    @Step ("Check cards name")
    public HomePage checkName() {
        cards.shouldBe(CollectionCondition.allMatch("Not all elements have names", card ->
                card.findElement(By.xpath(".//div[@class='inventory_item_name']")).isDisplayed()));
        return this;
    }



    @Override
    @Step ("Check footer links")
    public HomePage checkFooterLinks() {
        return (HomePage) super.checkFooterLinks();
    }


    @Override
    @Step ("Open burger menu")
    public HomePage openMenu() {
      return (HomePage) super.openMenu();
    }

    @Step ("Select menu section '{item}'")
    public HomePage chooseMenuItem(String item) {
        navBarItems.find(Condition.exactText(item)).click();
        return this;
    }

    @Step ("Check about opening")
    public HomePage checkAboutOpening() {
        checkUrl("https://saucelabs.com");
        return this;
    }

    @Step ("Check hover")
    public HomePage checkHover() {
       navBarItems.find(Condition.exactText("ABOUT")).hover().shouldHave(Condition.cssValue("color", "rgba(226, 35, 26, 1)"));
        return this;
    }

    @Step ("Add to Basket")
    public HomePage addToBasket() {
        ElementsCollection buttons = $$x(".//button[contains(@id, 'add-to-cart')]");
        buttons.get(new Random().nextInt(buttons.size())).shouldBe(Condition.visible).click();
        return this;
    }

    @Step ("Add product to Basket")
    public HomePage addToBasket(Product product) {
        SelenideElement card = cards.get(new Random().nextInt(cards.size()));
        card.$x(".//button[contains(@id, 'add-to-cart')]").shouldBe(Condition.visible).click();
        String name = card.$x(".//div[@class='inventory_item_name']").getText();
        String description = card.$x(".//div[@class='inventory_item_desc']").getText();
        Double price = Double.parseDouble(card.$x(".//div[@class='inventory_item_price']").getText().replaceAll("[$]", ""));
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return this;
    }

    @Step ("Remove from Basket")
    public HomePage removeFromBasket() {
        ElementsCollection buttons = $$x(".//button[contains(@id, 'remove')]");
        buttons.get(new Random().nextInt(buttons.size())).shouldBe(Condition.visible).click();
        return this;
    }

    @Override
    @Step ("Check product counter")
    public HomePage checkCounter(){
        return (HomePage) super.checkCounter();
    }

    @Override
    @Step ("Check product counter")
    public HomePage checkCounter(int count){
        return (HomePage) super.checkCounter();
    }

    @Override
    @Step ("Check empty basket")
    public HomePage checkEmptyBasket(){
        return ((HomePage) super.checkEmptyBasket());
    }

    @Step ("Open Product")
    public HomePage openProduct() {
        SelenideElement card = cards.get(new Random().nextInt(cards.size()));
        card.$x(".//div[@class='inventory_item_name']").shouldBe(Condition.visible).click();
        return this;
    }

    @Step ("Back to Products")
    public HomePage backToProducts() {
        backToProducts.shouldBe(Condition.visible).click();
        return this;
    }

    @Step ("check Logout")
    public LoginPage logout() {
        chooseMenuItem("LOGOUT");
        return new LoginPage();
    }



}
