package services;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class ClientDataService {
    private String baseUrl;
    public ClientDataService(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public Response getClient(String publicKey){
        return given().header("Content-Type", "application/json").when().get(baseUrl+"/merchants/"+publicKey);
    }
}