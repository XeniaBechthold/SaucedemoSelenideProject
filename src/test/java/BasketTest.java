import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import product.Product;


import static com.codeborne.selenide.Selenide.open;

public class BasketTest {

    private static final String URL = "https://www.saucedemo.com";

    @BeforeMethod
    public void setUp(){
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true));
        open(URL);
        new LoginPage().login("standard_user", "secret_sauce");
    }


    @Test
    @Description("check basket list size")
    public void checkBasketListSize(){
        new HomePage().addToBasket()
                .addToBasket()
                .openBasket()
                .checkPage()
                .checkProductListSize(2);
    }

    @Test
    @Description("Check products parameters")
    public void checkBasketProductsParameters(){
        Product product = new Product();
        new HomePage().addToBasket(product)
                .openBasket()
                .checkProduct(product);
    }

    @Test
    @Description("Check order field validation")
    public void checkFieldValidation(){
        new HomePage().addToBasket()
                .openBasket()
                .checkFieldValidation();
    }


    @Test
    @Description("Check total price")
    public void checkBasketTotalPrice(){
        // List<Product> productList = new ArrayList<Product>();
        Product product1 = new Product();
        Product product2 = new Product();
        new HomePage().addToBasket(product1)
                .addToBasket(product2)
                .openBasket()
                .orderForm("Test_name", "Test_last_name", "33609")
                .checkTotalProductPrice(product1.getPrice()+product2.getPrice());
    }

    @Test
    @Description("check remove products from basket")
    public void checkRemoveProductsFromBasket(){
        new HomePage().addToBasket()
                .addToBasket()
                .openBasket()
                .removeFromBasket()
                .checkProductListSize(1);
    }

    @Test
    @Description("check return to home page")
    public void checkReturnToHomePage(){
        new HomePage().addToBasket()
                .openBasket()
                .returnToHomePage()
                .checkPage();
    }

    @Test
    @Description("Finish Order")
    public void checkFinishOrder(){
        new HomePage().addToBasket()
                .addToBasket()
                .openBasket()
                .orderForm("Test_name", "Test_last_name", "33609")
                .finishOrder();
    }

    @Test
    @Description("back to home page after order")
    public void backToHomePageAfterOrder(){
        new HomePage().addToBasket()
                .addToBasket()
                .openBasket()
                .orderForm("Test_name", "Test_last_name", "33609")
                .finishOrder()
                .backToHomePageAfterOrder()
                .checkPage();
    }

}
