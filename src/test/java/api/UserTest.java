package api;

import api.pet.PetService;
import api.user.UserService;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class UserTest {


    @DataProvider(name = "createUsersNames")
    public Object[][] createUsersNames(){
        return new Object[][] {{"John123", "Oleg321"},
                {"AgentDonagan", "Oleg 456"},
                {"Cortes", "Oleg789"}};
    }

    @Test (dataProvider ="createUsersNames")
    @Description("Create user")
    public void createUser(String name){
        new UserService().createUser(name);
    }

    @Test (dataProvider ="createUsersNames")
    @Description("Get user")
    public void getUser(String name){
        new UserService().createUser(name)
                .getUserWithStatus(name, 200);
    }

    @Test
    @Description("Create/Read/Update/Delete")
    public void crudUser(){
        new UserService().createUser("Engin")
                .getUserWithStatus("Engin", 200)
                .updateUser("Engin", "Sercan")
                .getUserWithStatus("Engin", 404)
                .getUserWithStatus("Sercan", 200)
                .deleteUser("Sercan")
                .getUserWithStatus("Sercan", 404);
    }

    @Test
    @Description("Create user with Array")
    public void createUserWithArray(){
        String[] names = {"name1", "name2", "name3", "name4", "name5"};
        new UserService().createUserWithArray(names);
    }

}
