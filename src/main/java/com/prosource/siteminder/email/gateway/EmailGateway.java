package com.prosource.siteminder.email.gateway;

import com.prosource.siteminder.email.dto.EmailRequest;
import org.springframework.http.ResponseEntity;

public interface EmailGateway {

    String getName();
    ResponseEntity<String> send(EmailRequest emailRequest);
}
