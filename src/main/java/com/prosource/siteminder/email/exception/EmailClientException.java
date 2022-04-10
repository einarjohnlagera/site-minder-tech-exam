package com.prosource.siteminder.email.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailClientException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Throwable cause;

    public EmailClientException(HttpStatus httpStatus, String message) {
        this(httpStatus, null, message);
    }

    public EmailClientException(HttpStatus httpStatus, Throwable cause, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.cause = cause;
    }

}
