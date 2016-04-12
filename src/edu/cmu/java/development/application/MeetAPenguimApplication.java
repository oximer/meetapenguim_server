package edu.cmu.java.development.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import edu.cmu.java.development.application.services.AttributeService;
import edu.cmu.java.development.application.services.ContactService;
import edu.cmu.java.development.application.services.InboxMessageService;
import edu.cmu.java.development.application.services.ShareService;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by urbano on 4/5/16.
 */
public class MeetAPenguimApplication extends ResourceConfig {

    public MeetAPenguimApplication() {
        packages("edu.cmu.java.development.application.resources");
        register(ContactService.class);
        register(AttributeService.class);
        register(InboxMessageService.class);
        register(ShareService.class);
        register(JacksonJaxbJsonProvider.class);
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    }
}
