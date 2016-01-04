package be.valuya.ovh.sms.client;

import be.valuya.ovh.sms.domain.OvhRestException;
import be.valuya.ovh.sms.domain.SmsJob;
import be.valuya.ovh.sms.domain.SmsMessage;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class SmsClient {

    private static final String OVH_BASE_URL = "https://eu.api.ovh.com/1.0/";

    private transient Client jaxRsClient;

    /**
     * Empty constructor to be able to get a CDI proxy. Please init() before
     * use.
     *
     */
    public SmsClient() {
    }

    public SmsClient(String appSecret, String appKey, String consumerKey) {
        init(appSecret, appKey, consumerKey);
    }

    public SmsClient(Client jaxRsClient, String appSecret, String appKey, String consumerKey) {
        init(jaxRsClient, appSecret, appKey, consumerKey);
    }

    public void init(String appSecret, String appKey, String consumerKey) {
        try {
            SSLContext sslContext = SSLContext.getInstance("tlsv1");
            sslContext.init(null, null, null);

            Client client = ClientBuilder.newBuilder()
                    .sslContext(sslContext)
                    .build();
            init(client, appSecret, appKey, consumerKey);
        } catch (NoSuchAlgorithmException | KeyManagementException exception) {
            throw new IllegalStateException(exception);
        }
    }

    public void init(Client jaxRsClient, String appSecret, String appKey, String consumerKey) {
        this.jaxRsClient = jaxRsClient;
        OvhRequestFilter ovhRequestFilter = new OvhRequestFilter(appSecret, appKey, consumerKey);
        jaxRsClient.register(ovhRequestFilter);
    }

    public SmsJob sendSms(String serviceName, SmsMessage smsMessage) throws OvhRestException {
        if (jaxRsClient == null) {
            throw new IllegalStateException("Client not initialized");
        }

        URI smsUri = UriBuilder.fromUri(OVH_BASE_URL)
                .path("sms")
                .path("{serviceName}")
                .path("jobs")
                .build(serviceName);

        WebTarget target = jaxRsClient.target(smsUri);

        Entity<SmsMessage> smsEntity = Entity.entity(smsMessage, MediaType.APPLICATION_JSON_TYPE);
        SmsJob smsJob;
        try {
            smsJob = target.request()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8)
                    .post(smsEntity, SmsJob.class);

            return smsJob;
        } catch (WebApplicationException webApplicationException) {
            Response response = webApplicationException.getResponse();
            OvhRestException ovhRestException = response.readEntity(OvhRestException.class);
            ovhRestException.addSuppressed(webApplicationException);
            throw ovhRestException;
        }
    }

}
