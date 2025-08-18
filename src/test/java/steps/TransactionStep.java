package steps;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.Assert;
import services.ClientDataService;
import services.TransactionsService;

public class TransactionStep {
    private TransactionsService transactionsService;
    private Response response;
    private String acceptanceToken;
    private static final String base_Url = "https://api-sandbox.co.uat.wompi.dev/v1";
    private static final String private_Key = "pprv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg";

    @Given("se genere un token de aceptación válido {string}")
    public void generate_acceptance_token(String publicKey) {
        ClientDataService clientDataService = new ClientDataService(base_Url);
        Response authResponse = clientDataService.getClient(publicKey);
        Assert.assertEquals(200, authResponse.getStatusCode());
        acceptanceToken = authResponse.jsonPath().getString("data.presigned_acceptance.acceptance_token");
        transactionsService = new TransactionsService(base_Url, private_Key);
    }

    @When("el usuario envíe la solicitud")
    public void send_transaction_request(){
        response = transactionsService.generatePseTransaction(acceptanceToken);
        System.out.println(response.getBody().asString());
    }

    @Then("se deberá ver una respuesta de que la transacción fue exitosa")
    public void verify_transaction_response(){
        Assert.assertEquals(201, response.getStatusCode());
    }
}
