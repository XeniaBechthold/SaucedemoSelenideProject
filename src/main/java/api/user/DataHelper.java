package api.user;

public class DataHelper {
    public static UserRequest createUpdateUser(String userName){
        return UserRequest.builder()
                .id(0)
                .username(userName)
                .firstName("Test")
                .lastName("Test")
                .email("test+326575421@test.com")
                .password("1q2w3e4r5t")
                .phone("0123456789")
                .userStatus(0)
                .build();}

}
