package com.splan.bplan.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bplan.datacenter")
@Data
public class DataCenterConfig {

    private String url;

    private String cameoUrl;

    private String apiKey;

    private String apiVersion;
}
