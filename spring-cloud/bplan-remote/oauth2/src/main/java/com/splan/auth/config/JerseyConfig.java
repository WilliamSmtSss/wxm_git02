package com.splan.auth.config;


import com.splan.auth.rest.ClientSecretResource;
import com.splan.auth.rest.SecurityResource;
import com.splan.auth.rest.UserRoleResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;


/**
 * @author keets
 * @date 2017/9/5
 */
@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RequestContextFilter.class);
        register(ClientSecretResource.class);
        //配置restful package.
        register(SecurityResource.class);
        register(UserRoleResource.class);
    }
}