# ovh-sms-client
OVH sms client library in Java 8. Could be adapted to Java 7 without too much effort.

This library allows you to send an SMS using OVH REST api.

It uses JAX-RS 2 and tries to be portable and as clean as possible.

It just uses POJOs and standard JAX-RS to communicate with OVH. A [ClientRequestFilter](https://docs.oracle.com/javaee/7/api/javax/ws/rs/client/ClientRequestFilter.html) is used to sign requests according to OVH strange, useless signing requirements.

Contrary to other samples found on the web, including on OVH web site, it works with UTF-8 characters.

# Setup and testing
Create an API key and stuff [there](https://eu.api.ovh.com/createToken/?GET=/sms&GET=/sms/*/jobs&POST=/sms/*/jobs&duration=2592000) if you don't have them. You should grant _GET_ role on _/sms_ and _GET+POST_ on _/sms/*/jobs_ (no slashes at the end contrary to what OVH guide says, or it will fail with HTTP status 403!).

You can paste the generated app key/secret, consumer key and service name in SmsClientTest and remove @Ignore to test it.

# Send some SMS
```java
  SmsMessage smsMessage = new SmsMessage();
  smsMessage.setSender("valuya.be");
  smsMessage.setMessage("¿Qué pasa tronco?");
  smsMessage.setReceivers(Arrays.asList("+32498707213"));
  smsMessage.setSmsCoding(SmsCoding.EIGHT_BIT);

  // get some SmsClient
  // you'll probably want to get those params from your configuration
  SmsClient smsClient = new SmsClient(appSecret, appKey, consumerKey);
  // send it
  SmsJob smsJob = smsClient.sendSms(serviceName, smsMessage);

  List<String> invalidReceivers = smsJob.getInvalidReceivers(); // should be empty if everything is ok
```
