import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Validation {

    private static String ALGORITHM = "PBKDF2withHmacSHA512";
    private static final int Iteraction = 65536;
    private static final int key_lenght = 512;
    private static final int SALT_LENGHT = 16;


    public boolean verifyPassword(String inputPassword, String storedHashData) throws Exception{

        String[] parts = storedHashData.split(":");
        String saltbase64 = parts[0];
        String storedHashBase64 = parts[1];
        int iteration = Integer.parseInt(parts[2]);
        int keyLenght = Integer.parseInt(parts[3]);


        byte[] salt = Base64.getDecoder().decode(saltbase64);
        byte[] storedHash = Base64.getDecoder().decode(storedHashBase64);

        KeySpec spec = new PBEKeySpec(
                inputPassword.toCharArray(),
                salt,
                iteration,
                keyLenght
        );

        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] compute = factory.generateSecret(spec).getEncoded();

        return Arrays.equals(compute,storedHash);
    }
}
