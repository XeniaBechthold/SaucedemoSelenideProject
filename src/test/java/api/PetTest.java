package api;

import api.pet.PetService;
import api.pet.create.request.CreatePetRequest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class PetTest {


    @DataProvider(name = "createPetsNames")
    public Object[][] createPetsNames(){
        return new Object[][] {{"Tom"},
                {"Pushok"},
                {"Cat"}};
    }

    @Test (dataProvider ="createPetsNames")
    @Description("Create pet")
    public void createPet(String name){
        new PetService().createPet(name);
    }

    @Test (dataProvider ="createPetsNames")
    @Description("Update pet")
    public void updatePet(String name){
        new PetService().updatePet(name);
    }

    @Test
    @Description("Find by status")
    public void findByStatus(){
        new PetService().findByStatus("sold");
    }

    @Test
    @Description("Get by id")
    public void getPetById(){
        new PetService().getPetById(1);
    }

    @Test
    @Description("Image upload")
    public void uploadImage(){
        new PetService().getPetById(1);
    }

    @Test
    @Description("CreateAndDeletePet")
    public void createAndDeletePet(){
        PetService petService  = new PetService();
        petService.createPet("Tom")
                .getPetById(petService.getPetId())
                .deletePetById(petService.getPetId());
    }

    @Test
    @Description("CreateAndUpdatePet")
    public void createAndUpdatePet(){
        PetService petService  = new PetService();
        petService.createPet("Tom")
                .updatePetFormData("Jack", "sold", petService.getPetId())
                .getPetById(petService.getPetId());

        Assert.assertEquals(petService.getOldName(), "Jack");
    }
}
