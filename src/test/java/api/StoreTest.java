package api;

import api.pet.PetService;
import api.store.StoreService;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoreTest {

    @Test
    @Description("Place Order for Pet")
    public void placeOrder(){
        new StoreService().placeOrder(1);
    }

    @Test
    @Description("Find Order by Id")
    public void findOrder(){
        StoreService storeService = new StoreService();
        storeService.placeOrder(1)
                .findOrder(storeService.getOrderId());
    }

    @Test
    @Description("Delete Order by Id")
    public void deleteOrder(){
        StoreService storeService = new StoreService();
        storeService.placeOrder(1)
                .deleteOrder(storeService.getOrderId());

    }

    @Test
    @Description("Get Inventory")
    public void getInventory(){
        new StoreService().getInventory();

    }
}
