package api.pet;

import api.pet.create.Category;
import api.pet.create.Tag;
import api.pet.create.request.CreatePetRequest;

import java.util.ArrayList;

public class DataHelper {
    public static CreatePetRequest createPetBody(String name){
        return CreatePetRequest.builder()
            .id(0)
            .category(Category.builder().id(0).name("string").build())
            .name(name)
            .photoUrls(new ArrayList<String>())
            .tags(new ArrayList<Tag>())
            .status("available")
            .build();}
}
