package com.prosource.siteminder.email.config;

import com.prosource.siteminder.email.gateway.EmailClientErrorHandler;
import com.prosource.siteminder.email.gateway.EmailGateway;
import com.prosource.siteminder.email.gateway.ClientRestTemplate;
import com.prosource.siteminder.email.gateway.mailgun.MailGun;
import com.prosource.siteminder.email.gateway.sendgrid.SendGrid;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class EmailSenderConfig {

    @Bean
    public RestTemplate emailRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                          EmailClientErrorHandler errorHandler) {
        return restTemplateBuilder
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    public List<EmailGateway> emailGateWays(EmailProperties emailProperties, ClientRestTemplate clientRestTemplate) {
        List<EmailGateway> result = new ArrayList<>();
        for (EmailProperties.GatewayDetails gateway : emailProperties.getGateway()) {
            EmailGateway eg = Optional.ofNullable(getEmailGateway(gateway, clientRestTemplate))
                    .orElseThrow(() -> new UnsupportedOperationException(gateway.getName() + " is not yet implemented"));
            result.add(eg);
        }

        return result;
    }

    private EmailGateway getEmailGateway(EmailProperties.GatewayDetails gatewayDetails,
                                         ClientRestTemplate clientRestTemplate) {
        switch (gatewayDetails.getName().toLowerCase()) {
            case "mailgun":
                return new MailGun(gatewayDetails, clientRestTemplate);
            case "sendgrid":
                return new SendGrid();
            default:
                return null;
        }
    }


}
