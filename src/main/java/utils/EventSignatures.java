package utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EventSignatures {
    public static String generateEventChecksum(String body, String eventSecret){
        try{
            String data = body + eventSecret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        }catch (Exception e){
            throw new RuntimeException("Error generando checksum", e);
        }
    }
}
