package com.prosource.siteminder.email.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EmailSenderRequest {

    String from;
    String to;
    String cc;
    String bcc;
    String subject;
    String text;
}
