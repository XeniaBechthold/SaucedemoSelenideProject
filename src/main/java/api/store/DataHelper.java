package api.store;

import api.pet.create.Category;
import api.pet.create.Tag;
import api.pet.create.request.CreatePetRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class DataHelper {
    public static OrderRequest placeOrder(long petId){
        return OrderRequest.builder()
                .id(0)
                .petId(petId)
                .quantity(2)
                .shipDate("2023-02-21T15:57:39.978Z")
                .status("placed")
                .complete(true).build();}
}
