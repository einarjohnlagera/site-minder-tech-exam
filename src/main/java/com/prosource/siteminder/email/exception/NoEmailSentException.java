package com.prosource.siteminder.email.exception;

import com.prosource.siteminder.email.dto.ErrorResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class NoEmailSentException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Email not sent due to some client error";
    private final List<ErrorResponse> errors;

    public NoEmailSentException(List<ErrorResponse> errors) {
        super(DEFAULT_MESSAGE);
        this.errors = errors;
    }
}
