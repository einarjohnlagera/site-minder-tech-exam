package com.prosource.siteminder.email.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prosource.siteminder.email.dto.EmailClientResponse;
import com.prosource.siteminder.email.dto.EmailRequest;
import com.prosource.siteminder.email.dto.EmailResponse;
import com.prosource.siteminder.email.dto.ErrorResponse;
import com.prosource.siteminder.email.exception.EmailClientException;
import com.prosource.siteminder.email.exception.NoEmailSentException;
import com.prosource.siteminder.email.gateway.EmailGateway;
import com.prosource.siteminder.email.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    private final List<EmailGateway> emailGateways;

    public EmailClientResponse send(EmailRequest emailRequest) {
        List<ErrorResponse> errors = new ArrayList<>();
        for (EmailGateway gateway : emailGateways) {
            try {
                ResponseEntity<String> response = gateway.send(emailRequest);
                if (response.getStatusCode().is2xxSuccessful()) {
                    return new EmailClientResponse(gateway.getName(), response.getBody());
                } else {
                    String errorMessage = String.format("Gateway %s returned unsuccessful status %s",
                            gateway.getName(), response.getStatusCode());
                    errors.add(new ErrorResponse(gateway.getName(), errorMessage));
                }
            } catch (EmailClientException e) {
                String errorMessage = String.format(
                        "An error occurred while sending thru %s gateway. HTTP Status: %s Response Body: %s",
                        gateway.getName(), e.getHttpStatus(), e.getMessage());
                log.error(errorMessage, e);
                errors.add(new ErrorResponse(gateway.getName(), errorMessage));
            }
        }
        throw new NoEmailSentException(errors);
    }

}
