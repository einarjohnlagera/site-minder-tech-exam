package com.prosource.siteminder.email.service;

import com.prosource.siteminder.email.dto.EmailClientResponse;
import com.prosource.siteminder.email.dto.EmailRequest;
import com.prosource.siteminder.email.dto.EmailResponse;
import com.prosource.siteminder.email.exception.NoEmailSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailSender sender;

    public EmailResponse send(EmailRequest email) {
        try {
            EmailClientResponse response = sender.send(email);
            return new EmailResponse(response.getGateway(), response.getBody(), null);
        } catch (NoEmailSentException e) {
            log.error("No mail was sent due to errors", e);
            return new EmailResponse(null, e.getMessage(), e.getErrors());
        }
    }
}
