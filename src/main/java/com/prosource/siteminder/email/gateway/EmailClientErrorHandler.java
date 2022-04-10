package com.prosource.siteminder.email.gateway;

import com.prosource.siteminder.email.exception.EmailClientException;
import com.prosource.siteminder.email.exception.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class EmailClientErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        verifyIfRetryable(response.getStatusCode());
        String errorMessage;
        try {
            errorMessage = new BufferedReader(
                    new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            errorMessage = e.getMessage();
        }
        throw new EmailClientException(response.getStatusCode(), errorMessage);
    }

    private void verifyIfRetryable(HttpStatus httpStatus) {
        if (HttpStatus.SERVICE_UNAVAILABLE == httpStatus
                || HttpStatus.GATEWAY_TIMEOUT == httpStatus) {
            throw new RetryableException();
        }
    }
}
