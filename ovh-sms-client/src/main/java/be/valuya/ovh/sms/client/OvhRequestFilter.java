package be.valuya.ovh.sms.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

public class OvhRequestFilter implements ClientRequestFilter, WriterInterceptor {

    private final String appSecret;
    private final String appKey;
    private final String consumerKey;
    private ClientRequestContext requestContext;

    public OvhRequestFilter(String appSecret, String appKey, String consumerKey) {
        this.appSecret = appSecret;
        this.appKey = appKey;
        this.consumerKey = consumerKey;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        this.requestContext = requestContext;
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        OutputStream originalOutputStream = context.getOutputStream();

        OvhSigningOutputStream signingOutputStream = new OvhSigningOutputStream(originalOutputStream);
        String method = requestContext.getMethod();
        URI uri = requestContext.getUri();

        MultivaluedMap<String, Object> headerMap = requestContext.getHeaders();

        signingOutputStream.init(appSecret, appKey, consumerKey, method, uri, headerMap);

        context.setOutputStream(signingOutputStream);

        context.proceed();
    }

}
