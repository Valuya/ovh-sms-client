package be.valuya.ovh.sms.client;
// Customized {@code ContextResolver} implementation to pass ObjectMapper to use

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public JacksonContextResolver() throws Exception {
        ResteasyJackson2Provider resteasyJacksonProvider = new ResteasyJackson2Provider();
        objectMapper = resteasyJacksonProvider.locateMapper(String.class, MediaType.APPLICATION_JSON_TYPE);
        resteasyJacksonProvider.setMapper(objectMapper);
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        objectMapper.registerModule(jaxbModule);
    }

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}
