package be.valuya.ovh.sms.client;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.xml.bind.DatatypeConverter;

public class Sha1Utils {

    public static String hashSHA1(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(bytes);
            byte[] hash = messageDigest.digest();
            return DatatypeConverter.printHexBinary(hash).toLowerCase(Locale.ENGLISH);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException); // we can just assume it's implemented in our JVM
        }
    }

}
