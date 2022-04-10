package com.prosource.siteminder.email.controller;

import com.prosource.siteminder.email.dto.EmailRequest;
import com.prosource.siteminder.email.dto.EmailResponse;
import com.prosource.siteminder.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmailResponse send(@RequestBody @Valid EmailRequest body) {
        return emailService.send(body);
    }
}
