package com.splan.gateway.apigateway.config;

import org.springframework.context.ApplicationEvent;

public class DynamicRouteInitEvent extends ApplicationEvent {
    public DynamicRouteInitEvent(Object source) {
        super(source);
    }
}
