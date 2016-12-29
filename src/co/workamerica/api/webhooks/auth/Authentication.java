package co.workamerica.api.webhooks.auth;

import org.glassfish.jersey.server.ResourceConfig;


/**
 * Created by Faizan on 7/26/2016.
 */
public class Authentication extends ResourceConfig {

    public Authentication () {
        packages("co.workamerica.api.webhooks");

        //Register Auth Filter here
        register(AuthenticationFilter.class);
    }
}
