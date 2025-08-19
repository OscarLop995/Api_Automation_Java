package steps;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import services.EventService;

public class EventStep {
    private EventService eventService;
    private Response response;
    private String body;

    @Given("que exista algún  evento configurado")
    public void eventValidation(){
        eventService = new EventService("http://localhost:8080/webhook", "stagtest_events_2PDUmhMywUkvb1LvxYnayFbmofT7w39N");
    }

    @When("se envíe el evento")
    public void sendEvent(){
        response = eventService.sendEvent();
    }

    @When("se envíe el evento con firma válida")
    public void sendValidSignature(){
        response = eventService.sendEvent();
    }

    @When("se envíe el evento con firma inválida")
    public void sendInvalidSignature(String body){
        this.body = body;
        response = eventService.sendEvent(body);
    }

    @Then("el webhook deberá enviar una respuesta {int}")
    public void verifyResponse(Integer expectedStatus){
        Assert.assertEquals(expectedStatus.intValue(), response.getStatusCode());
    }
}
