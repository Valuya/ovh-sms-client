package be.valuya.ovh.sms.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Yannick Majoros <yannick@valuya.be>
 */
class OvhSigningOutputStream extends OutputStream {

    private final OutputStream wrappedOutputStream;
    private final ByteArrayOutputStream bodyBufferOutputStream;
    private MessageDigest messageDigest;
    private String appKey;
    private String consumerKey;
    private MultivaluedMap<String, Object> headerMap;

    public OvhSigningOutputStream(OutputStream wrappedOutputStream) {
        this.wrappedOutputStream = wrappedOutputStream;
        bodyBufferOutputStream = new ByteArrayOutputStream();
    }

    public void init(String appSecret, String appKey, String consumerKey, String method, URI uri, MultivaluedMap<String, Object> headerMap) {
        this.consumerKey = consumerKey;
        this.appKey = appKey;
        this.headerMap = headerMap;
        String uriStr = uri.toString();

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            updateDigest(appSecret);
            updateDigest("+");
            updateDigest(consumerKey);
            updateDigest("+");
            updateDigest(method);
            updateDigest("+");
            updateDigest(uriStr);
            updateDigest("+");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException); // we can just assume it's implemented in our JVM
        }
    }

    @Override
    public void write(byte[] inputBytes, int offset, int length) throws IOException {
        bodyBufferOutputStream.write(inputBytes, offset, length);

        messageDigest.update(inputBytes, offset, length);
    }

    @Override
    public void write(byte[] inputBytes) throws IOException {
        bodyBufferOutputStream.write(inputBytes);
        messageDigest.update(inputBytes);
    }

    @Override
    public void write(int inputByte) throws IOException {
        bodyBufferOutputStream.write(inputByte);
        messageDigest.update((byte) inputByte);
    }

    @Override
    public void close() throws IOException {
        Date date = new Date();
        long timeStamp = date.getTime() / 1000;
        String timeStampStr = Long.toString(timeStamp);
        updateDigest("+");
        updateDigest(timeStampStr);
        byte[] hashBytes = messageDigest.digest();
        String hashHex = DatatypeConverter.printHexBinary(hashBytes).toLowerCase(Locale.ENGLISH);
        String signature = "$1$" + hashHex;

        headerMap.add("X-Ovh-Application", appKey);
        headerMap.add("X-Ovh-Consumer", consumerKey);
        headerMap.add("X-Ovh-Signature", signature);
        headerMap.add("X-Ovh-Timestamp", timeStamp);

        Object contentType = headerMap.getFirst(javax.ws.rs.core.HttpHeaders.CONTENT_TYPE);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_JSON;
        }
        if (!contentType.toString().contains("charset")) {
            headerMap.putSingle(javax.ws.rs.core.HttpHeaders.CONTENT_TYPE, contentType + ";charset=UTF-8");
        }
        
        byte[] bodyBufferBytes = bodyBufferOutputStream.toByteArray();
        wrappedOutputStream.write(bodyBufferBytes);
    }

    private void updateDigest(String str) {
        messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
    }

}
