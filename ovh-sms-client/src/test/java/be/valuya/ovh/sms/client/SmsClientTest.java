package be.valuya.ovh.sms.client;

import be.valuya.ovh.sms.domain.SmsCoding;
import be.valuya.ovh.sms.domain.SmsJob;
import be.valuya.ovh.sms.domain.SmsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author devyam
 */
public class SmsClientTest {

    @Test
    @Ignore
    public void testSendSms() {
        // auth and account data
        String appKey = "xxxxx";
        String appSecret = "yyyyyyyyyyyyyyyyyyyyy";
        String consumerKey = "abcdefgabcdefgabcdefgabcdefgabcdefg";
        String serviceName = "zzzzzzzz";

        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setSender("valuya.be");
        smsMessage.setMessage("¿Qué pasa tronco?");
        smsMessage.setReceivers(Arrays.asList("+32498707213"));
        smsMessage.setSmsCoding(SmsCoding.EIGHT_BIT);

        Client jaxRsClient = getRestEasyJaxRsClient();

        SmsClient smsClient = new SmsClient(jaxRsClient, appSecret, appKey, consumerKey);
        SmsJob smsJob = smsClient.sendSms(serviceName, smsMessage);

        List<String> invalidReceivers = smsJob.getInvalidReceivers();

        Assert.assertNotNull(smsJob);
        Assert.assertTrue(invalidReceivers.isEmpty());
    }

    private Client getRestEasyJaxRsClient() {
        ResteasyJackson2Provider resteasyJacksonProvider = new ResteasyJackson2Provider();
        ObjectMapper objectMapper = resteasyJacksonProvider.locateMapper(String.class, MediaType.APPLICATION_JSON_TYPE);
        resteasyJacksonProvider.setMapper(objectMapper);
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        objectMapper.registerModule(jaxbModule);
        Client jaxRsClient = new ResteasyClientBuilder().register(resteasyJacksonProvider).build();
        return jaxRsClient;
    }

}
