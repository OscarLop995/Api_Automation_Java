package steps;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.Assert;
import services.ClientDataService;


public class GetMerchantStep {
    private ClientDataService clientDataService;
    private Response response;
    private String publicKey;


    @Given("se desee consultar la información del cliente con llave {string}")
    public void validKey(String key){
        clientDataService = new ClientDataService( "https://api-sandbox.co.uat.wompi.dev/v1");
        publicKey = key;
    }

    @When("envíe la solicitud")
    public void sendRequest(){
        response = clientDataService.getClient(publicKey);
        System.out.println(response.getBody().asString());
    }

    @Then("deberá responder 200 y mostrarse la respuesta con la data del cliente")
    public void verifyValidKey(){
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("deberá indicar que no existe el cliente")
    public void verifyInvalidKey(){
        Assert.assertEquals(404, response.getStatusCode());
    }

    @Then("deberá indicar que es requerida la llave")
    public  void verifyNoKey(){
        Assert.assertEquals(401, response.getStatusCode());
    }
}