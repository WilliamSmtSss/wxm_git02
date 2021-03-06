package com.splan.bplan.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bplan.msg.international")
@Data
public class MessageInternationalConfig {

    private String url;

    private String account;

    private String password;

    private boolean open;
}
