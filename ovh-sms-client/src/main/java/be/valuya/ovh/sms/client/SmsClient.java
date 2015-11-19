package be.valuya.ovh.sms.client;

import be.valuya.ovh.sms.domain.SmsJob;
import be.valuya.ovh.sms.domain.SmsMessage;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class SmsClient {

    private static final String OVH_BASE_URL = "https://eu.api.ovh.com/1.0/";

    private final Client client;

    public SmsClient(String appSecret, String appKey, String consumerKey) {
        OvhRequestFilter ovhRequestFilter = new OvhRequestFilter(appSecret, appKey, consumerKey);
        client = ClientBuilder.newBuilder()
                        .build();
        client.register(ovhRequestFilter);
    }

    public SmsJob sendSms(String serviceName, SmsMessage smsMessage) {
        URI smsUri = UriBuilder.fromUri(OVH_BASE_URL)
                        .path("sms")
                        .path("{serviceName}")
                        .path("jobs")
                        .build(serviceName);

        WebTarget target = client.target(smsUri);

        Entity<SmsMessage> smsEntity = Entity.entity(smsMessage, MediaType.APPLICATION_JSON_TYPE);

        SmsJob smsJob = target.request()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8)
                        .post(smsEntity, SmsJob.class);

        return smsJob;
    }

}
