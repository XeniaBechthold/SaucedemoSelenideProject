package api.store;

import api.store.DataHelper;
import api.pet.PetService;
import api.pet.create.request.CreatePetRequest;
import api.pet.create.response.CreatePetResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class StoreService {

    public long getOrderId() {
        return orderId;
    }

    long orderId;

    RequestSpecification requestSpecification = given()
            .baseUri("https://petstore.swagger.io/v2")
            .basePath("store")
            .contentType(ContentType.JSON);

    @Step("Place an order for a pet '{petId}'")
    public StoreService placeOrder(long petId){
       OrderRequest createOrder = DataHelper.placeOrder(petId);
        OrderRequest placeNewOrder = requestSpecification
                .body(createOrder)
                .when()
                .post("order")
                .then()
                .statusCode(200)
                .extract().body().as(OrderRequest.class);
        Assert.assertEquals(placeNewOrder.getQuantity(), createOrder.quantity);
        orderId = placeNewOrder.getId();
        return this;
    }

    @Step("Find an order by id")
    public StoreService findOrder(long orderId){

        OrderRequest findOrder = requestSpecification
                .pathParams("orderId", orderId)
                .when()
                .get("order/{orderId}")
                .then()
                .statusCode(200)
                .extract().body().as(OrderRequest.class);
        Assert.assertEquals(findOrder.getPetId(), 1);
        return this;
    }

    @Step("Delete the order by id")
    public StoreService deleteOrder(long orderId){

        OrderRequest deleteOrder = requestSpecification
                .pathParams("orderId", orderId)
                .when()
                .delete("order/{orderId}")
                .then()
                .statusCode(200)
                .extract().body().as(OrderRequest.class);
        return this;
    }

    @Step("Get inventory")
    public StoreService getInventory(){
        OrderRequest getInventory = requestSpecification
                .when()
                .get("inventory")
                .then()
                .statusCode(200)
                .extract().body().as(OrderRequest.class);
        return this;
    }
}


