package api.pet;
import api.BaseService;
import api.pet.create.request.CreatePetRequest;
import api.pet.create.response.CreatePetResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import static io.restassured.RestAssured.given;
import java.io.File;


public class PetService extends BaseService {
    public long getPetId() {
        return petId;
    }

    public String getOldName() {
        return oldName;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    long petId;

    String oldName;

    String oldStatus;
    RequestSpecification requestSpecification = baseRequestSpecification
            .basePath("pet");

    @Step ("Create Pet '{petName}'")
    public PetService createPet(String petName){
        CreatePetRequest createPetBody = DataHelper.createPetBody(petName);
        CreatePetResponse createPetResponse = requestSpecification
                .body(createPetBody)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().body().as(CreatePetResponse.class);
        Assert.assertEquals(createPetResponse.getName(), createPetBody.getName());
        petId = createPetResponse.getId();
        return this;
    }

    @Step ("Update Pet '{petName}'")
    public PetService updatePet(String petName){
        CreatePetRequest createPetBody = DataHelper.createPetBody(petName);
        CreatePetResponse createPetResponse = requestSpecification
                .body(createPetBody)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().body().as(CreatePetResponse.class);
        Assert.assertEquals(createPetResponse.getName(), createPetBody.getName());
        return this;
    }

    @Step ("Update Pet with Form Data '{newName}' '{newStatus}' ")
    public PetService updatePetFormData(String newName, String newStatus, long id){
        requestSpecification
                .contentType(ContentType.URLENC)
                .pathParams("petId", id)
                .queryParam("name", newName)
                .queryParam("status", newStatus)
                .when()
                .post("{petId}")
                .then()
                .statusCode(200);

        return this;
    }

    @Step ("Find by '{status}'")
    public PetService findByStatus(String status){

        CreatePetResponse createPetResponse[] =requestSpecification
                .queryParam("status", status)
                .when()
                .get("findByStatus")
                .then()
                .statusCode(200)
                .extract().body().as(CreatePetResponse[].class);

       for (int i = 0; i < createPetResponse.length; i++) {
            Assert.assertEquals(createPetResponse[i].getStatus(),status);
        }
        return this;
    }

    @Step ("Get pet by '{id}'")
    public PetService getPetById(long id){

        CreatePetResponse createPetResponse =requestSpecification
                .pathParams("petId", id)
                .when()
                .get("{petId}")
                .then()
                .statusCode(200)
                .extract().body().as(CreatePetResponse.class);

            Assert.assertEquals(createPetResponse.getId(), id);
        oldName = createPetResponse.getName();
        oldStatus = createPetResponse.getStatus();
        return this;
    }

    @Step ("Upload Image")
    public PetService uploadImage(long id) {
        String path = "https://www.rd.com/wp-content/uploads/2021/01/GettyImages-1175550351.jpg";
        CreatePetResponse createPetResponse = requestSpecification
                .contentType(ContentType.MULTIPART)
                .pathParams("petId", id)
                .multiPart("file", new File(path))
                .when()
                .get("{petId}/uploadImage")
                .then()
                .statusCode(200)
                .extract().body().as(CreatePetResponse.class);
        return this;
    }

    @Step ("Delete pet by '{id}'")
    public PetService deletePetById(long id){
        CreatePetResponse createPetResponse =requestSpecification
                .pathParams("petId", id)
                .when()
                .delete("{petId}")
                .then()
                .statusCode(200)
                .extract().body().as(CreatePetResponse.class);
        return this;
    }

}
