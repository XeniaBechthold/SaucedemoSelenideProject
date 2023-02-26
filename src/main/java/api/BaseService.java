package api;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseService {

    protected RequestSpecification baseRequestSpecification = given()
            .baseUri("https://petstore.swagger.io/v2")
            .contentType(ContentType.JSON);
}
