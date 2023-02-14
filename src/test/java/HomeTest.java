import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class HomeTest {

    private static final String URL = "https://www.saucedemo.com";

    @BeforeMethod
    public void setUp(){
        open(URL);
        new LoginPage().login("standard_user", "secret_sauce");
    }

    @Test
    @Description("Check Elements")
    public void checkGeneralElements(){
        new HomePage().checkGeneralElements();
    }

    @Test
    @Description("Check cards list")
    public void checkCardsList(){
        new HomePage().checkCardsList();
    }

    @Test
    @Description("Check default sort")
    public void checkDefaultSort(){
        new HomePage().checkDefaultSort();
    }

    @Test
    @Description("Check reverse sort")
    public void checkReverseSort(){
        new HomePage().checkReverseSort();
    }

    @Test
    @Description("Check default price sort")
    public void checkPriceDefaultSort(){
        new HomePage().checkPriceDefaultSort();
    }

    @Test
    @Description("Check reverse price sort")
    public void checkPriceReverseSort(){
        new HomePage().checkPriceDefaultSort();
    }

    @Test
    @Description("Check price")
    public void checkPrice(){
        new HomePage().checkPrice();
    }

    @Test
    @Description("Check button")
    public void checkButton(){
        new HomePage().checkButton();
    }

    @Test
    @Description("Check name")
    public void checkName(){
        new HomePage().checkName();
    }

    @Test
    @Description("Check image")
    public void checkImage(){
        new HomePage().checkImage();
    }

    @Test
    @Description("Check footer links")
    public void checkFooterLinks(){
        new HomePage().checkFooterLinks();
    }

    @Test
    @Description("Check about section")
    public void checkAboutSection(){
        new HomePage().openMenu()
                .chooseMenuItem("ABOUT")
                .checkAboutOpening();
    }

    @Test
    @Description("Check hover")
    public void checkHover(){
        new HomePage().openMenu()
                .checkHover();
    }
}
