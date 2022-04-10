package com.prosource.siteminder.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    private String id;
    private String message;
}
