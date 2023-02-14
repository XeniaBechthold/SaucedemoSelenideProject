import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage{

    private final SelenideElement pageName = $x("//span[.='Products']");
    private final SelenideElement basket = $x("//div[@id='shopping_cart_container']/a");
    private final SelenideElement sort = $x("//span[@class='select_container']");
    private final SelenideElement sortOptions = $x("//select[@class='product_sort_container']");
    private final SelenideElement burgerMenuButton = $x("//button[@id='react-burger-menu-btn']");
    private final ElementsCollection cards = $$x ("//div[@class='inventory_item']");
    private final ElementsCollection footerLinks = $$x ("//footer/ul[@class='social']/li/a");
    private final ElementsCollection navBarItems = $$x ("//nav/a");



    @Step ("Check correct page is opened")
    public HomePage checkPage() {
        pageName.shouldBe(Condition.visible);
        return this;
    }

    @Step ("Check general elements on page")
    public HomePage checkGeneralElements() {
        basket.shouldBe(Condition.visible);
        sort.shouldBe(Condition.visible);
        burgerMenuButton.shouldBe(Condition.visible);
        return this;
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



    @Step ("Check footer links")
    public HomePage checkFooterLinks() {
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
    public HomePage openMenu() {
      burgerMenuButton.shouldBe(Condition.visible).click();
        return this;
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

}
