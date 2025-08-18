package steps;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.Assert;
import services.ClientDataService;
import static org.junit.Assert.assertEquals;

public class GetMerchantStep {
    private ClientDataService clientDataService;
    private Response response;
    private String publicKey;


    @Given("se desee consultar la información del cliente con llave {string}")
    public void valid_key(String key){
        clientDataService = new ClientDataService( "https://api-sandbox.co.uat.wompi.dev/v1");
        publicKey = key;
    }

    @When("envíe la solicitud")
    public void send_request(){
        response = clientDataService.getClient(publicKey);
        System.out.println(response.getBody().asString());
    }

    @Then("deberá responder 200 y mostrarse la respuesta con la data del cliente")
    public void verify_valid_key(){
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("deberá indicar que no existe el cliente")
    public void verify_invalid_key(){
        Assert.assertEquals(404, response.getStatusCode());
    }

    @Then("deberá indicar que es requerida la llave")
    public  void verify_no_key(){
        Assert.assertEquals(401, response.getStatusCode());
    }
}