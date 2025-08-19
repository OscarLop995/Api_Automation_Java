package webhook;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EventSignatures;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private final String eventSecret = "stagtest_events_2PDUmhMywUkvb1LvxYnayFbmofT7w39N";

    @PostMapping
    public ResponseEntity<String> receiveEvent(@RequestBody String payload, @RequestHeader("X-Event-Checksum") String checksumHeader) {
        try {
            JSONObject json = new JSONObject(payload);
            JSONObject transaction = json.getJSONObject("data").getJSONObject("transaction");

            String transactionId = transaction.getString("id");
            String transactionStatus = transaction.getString("status");
            int amountInCents = transaction.getInt("amount_in_cents");
            int timestamp = json.getInt("timestamp");

            String checksumGenerated = EventSignatures.generateEventChecksum(
                    transactionId, transactionStatus, amountInCents, timestamp, eventSecret
            );

            if (!checksumGenerated.equals(checksumHeader)) {
                System.out.println("Checksum esperado: " + checksumGenerated + ", recibido: " + checksumHeader);
                return ResponseEntity.badRequest().build();
            }

            System.out.println("Evento recibido exitosamente: " + payload);
            return ResponseEntity.ok("Evento recibido exitosamente");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error procesando evento: " + e.getMessage());
        }
    }
}
