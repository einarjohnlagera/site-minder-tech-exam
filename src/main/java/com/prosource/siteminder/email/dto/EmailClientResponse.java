package com.prosource.siteminder.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailClientResponse {

    private String gateway;
    private String body;
}
