import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Hashing {

    private static String ALGORITHM = "PBKDF2withHmacSHA512";
    private static final int Iteration = 65536;
    private static final int KEY_LENGHT = 512;
    private static final int SALT_LENGHT = 16;


    //hashing method

    public String hashPassword(String passowrd) throws Exception{

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGHT];
        random.nextBytes(salt);

        KeySpec spec  = new PBEKeySpec(
                passowrd.toCharArray(),
                salt,
                Iteration,
                KEY_LENGHT
        );

        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        String hashedPassword = String.format("%s:%s:%d:%d",saltBase64,hashBase64,Iteration,KEY_LENGHT);

        return hashedPassword;
    }
}
