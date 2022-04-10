package com.prosource.siteminder.email.gateway.sendgrid;

import com.prosource.siteminder.email.dto.EmailRequest;
import com.prosource.siteminder.email.gateway.EmailGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SendGrid implements EmailGateway {
    @Override
    public String getName() {
        return "SendGrid";
    }

    @Override
    public ResponseEntity<String> send(EmailRequest emailRequest) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
