package be.valuya.ovh.sms.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

public class OvhRequestFilter implements ClientRequestFilter {

    private final String appSecret;
    private final String appKey;
    private final String consumerKey;

    public OvhRequestFilter(String appSecret, String appKey, String consumerKey) {
        this.appSecret = appSecret;
        this.appKey = appKey;
        this.consumerKey = consumerKey;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        Date date = new Date();
        long timeStamp = date.getTime() / 1000;

        String method = requestContext.getMethod();
        URI uri = requestContext.getUri();

        String body = getBody(requestContext);

        String toSign = appSecret + "+" + consumerKey + "+" + method + "+" + uri + "+" + body + "+" + timeStamp;
        String signature = "$1$" + Sha1Utils.hashSHA1(toSign);

        MultivaluedMap<String, Object> headerMap = requestContext.getHeaders();
        headerMap.putSingle("X-Ovh-Application", appKey);
        headerMap.putSingle("X-Ovh-Consumer", consumerKey);
        headerMap.putSingle("X-Ovh-Signature", signature);
        headerMap.putSingle("X-Ovh-Timestamp", timeStamp);

        headerMap.putSingle(javax.ws.rs.core.HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        requestContext.setEntity(body);
    }

    private String getBody(ClientRequestContext requestContext) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        Object entity = requestContext.getEntity();
        String body = objectMapper.writeValueAsString(entity);
        return body;
    }

    private ObjectMapper getObjectMapper() {
        ResteasyJackson2Provider resteasyJacksonProvider = new ResteasyJackson2Provider();
        ObjectMapper objectMapper = resteasyJacksonProvider.locateMapper(String.class, MediaType.APPLICATION_JSON_TYPE);
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        objectMapper.registerModule(jaxbModule);
        return objectMapper;
    }

}
