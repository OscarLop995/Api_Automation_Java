package webhook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EventSignatures;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private final String eventSecret = "stagtest_events_2PDUmhMywUkvb1LvxYnayFbmofT7w39N";

    @PostMapping
    public ResponseEntity<String> receiveEvent(@RequestBody String payload, @RequestHeader("X-Event-Checksum")String checksumHeader) {
        String checksumGenerated = EventSignatures.generateEventChecksum(payload, eventSecret);
        if (!checksumGenerated.equals(checksumHeader)) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Evento recibido exitosamente" + payload);
        return ResponseEntity.ok("Evento recibido exitosamente");
    }
}
