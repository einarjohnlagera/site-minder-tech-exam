package com.prosource.siteminder.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {

    private List<GatewayDetails> gateway;

    @Data
    public static class GatewayDetails {
        private String name;
        private String apiKey;
        private String url;
        private String from;
    }
}
