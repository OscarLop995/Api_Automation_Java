package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signatures {
    public static String generate_signature(String reference, int amount, String currency, String integritySecret) {
        try{
            String concatenateData = reference +  amount + currency + integritySecret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(concatenateData.getBytes("UTF-8"));
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
            throw new RuntimeException("Error generando la firma", e);
        }
    }
}
