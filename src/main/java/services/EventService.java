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

    public Response sendEvent(){
        String transactionId = "123-456";
        String transactionStatus = "APPROVED";
        int amountInCents = 150000;
        int timestamp = 1530291411;
        String checksum = EventSignatures.generateEventChecksum(transactionId, transactionStatus, amountInCents, timestamp, eventSecret);
        String body = "{"
                + "\"event\":\"transaction.updated\","
                + "\"data\":{"
                + "     \"transaction\":{"
                + "         \"id\":\"" + transactionId + "\","
                + "         \"status\":\"" + transactionStatus + "\","
                + "         \"amount_in_cents\":" + amountInCents
                + "     }"
                + "},"
                + "\"environment\":\"test\","
                + "\"signature\":{"
                + "     \"properties\":[\"id\",\"status\",\"amount_in_cents\"],"
                + "     \"checksum\":\"" + checksum + "\""
                + "},"
                + "\"timestamp\":" + timestamp
                + "}";
        System.out.println("Checksum: " + checksum);
        return given().header("Content-Type", "application/json").header("X-Event-Checksum", checksum).body(body).when().post(webhookUrl).then().extract().response();
    }

    public Response sendEvent(String body) {
        String checksum = new org.json.JSONObject(body)
                .getJSONObject("signature")
                .getString("checksum");

        System.out.println("Checksum enviado desde el step/feature: " + checksum);

        return given()
                .header("Content-Type", "application/json")
                .header("X-Event-Checksum", checksum)
                .body(body)
                .when()
                .post(webhookUrl)
                .then()
                .extract().response();
    }
}
