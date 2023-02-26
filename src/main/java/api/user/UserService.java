package api.user;

import api.BaseService;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class UserService extends BaseService {

    RequestSpecification requestSpecification = baseRequestSpecification
            .basePath("user");


    @Step("Create User  '{userName}'")
    public UserService createUser(String userName){
      UserRequest createUser = DataHelper.createUpdateUser(userName);
        requestSpecification
                .body(createUser)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().body().as(UserRequest.class);
        return this;
    }

    @Step("Get User  '{userName}' expect status '{statusCode}'")
    public UserService getUserWithStatus(String userName, int statusCode){
        UserRequest getUser = requestSpecification
                .pathParam("userName", userName)
                .when()
                .get("{userName}")
                .then()
                .statusCode(statusCode)
                .extract().body().as(UserRequest.class);
        if(statusCode==200) {
            Assert.assertEquals(getUser.getUsername(), userName);}
        return this;
    }

    @Step("Update User  '{userName}'")
    public UserService updateUser(String userName, String newName){
        UserRequest createUser = DataHelper.createUpdateUser(newName);
        requestSpecification
                .body(createUser)
                .pathParam("userName", userName)
                .when()
                .put("{userName}")
                .then()
                .statusCode(200)
                .extract().body().as(UserRequest.class);
        return this;
    }

    @Step("Get User  '{userName}'")
    public UserService deleteUser(String userName){
       requestSpecification
                .pathParam("userName", userName)
                .when()
                .delete("{userName}")
                .then()
                .statusCode(200)
                .extract().body().as(UserRequest.class);
        return this;
    }

    @Step("Create User with Array '{userName}'")
    public UserService createUserWithArray(String[] userName){

        UserRequest[] createUser = new UserRequest[userName.length];
        for (int i = 0; i < userName.length; i++) {
           createUser[i] = DataHelper.createUpdateUser(userName[i]);
        }

        requestSpecification
                .body(createUser)
                .when()
                .post("createWithArray")
                .then()
                .statusCode(200)
                .extract().body().as(UserRequest.class);
        return this;
    }


}


