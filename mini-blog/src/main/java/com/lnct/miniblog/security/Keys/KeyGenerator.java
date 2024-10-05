import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.security.Key;

public class KeyGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);  // Generate a secure key for HS512
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());  // Encode key to Base64
        System.out.println(base64Key);  // Print the key so you can copy it
    }
}
