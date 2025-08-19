package services;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import utils.EventSignatures;

public class EventService {
    private final String webhookUrl;
    private final String eventSecret;

    public EventService(String webhookUrl, String eventSecret){
        this.webhookUrl = webhookUrl;
        this.eventSecret = eventSecret;
    }

    public Response sendEvent(String eventBody){
        String checksum = EventSignatures.generateEventChecksum(eventBody, eventSecret);
        return given().header("Content-Type", "application/json").header("X-Event-Checksum", checksum).body(eventBody).when().post(webhookUrl).then().extract().response();
    }
}
