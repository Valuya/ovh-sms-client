package be.valuya.ovh.sms.client;

import be.valuya.ovh.sms.domain.SmsCoding;
import be.valuya.ovh.sms.domain.SmsJob;
import be.valuya.ovh.sms.domain.SmsMessage;
import java.util.Arrays;
import java.util.List;
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

        SmsClient smsClient = new SmsClient(appSecret, appKey, consumerKey);
        SmsJob smsJob = smsClient.sendSms(serviceName, smsMessage);

        List<String> invalidReceivers = smsJob.getInvalidReceivers();

        Assert.assertNotNull(smsJob);
        Assert.assertTrue(invalidReceivers.isEmpty());
    }

}
