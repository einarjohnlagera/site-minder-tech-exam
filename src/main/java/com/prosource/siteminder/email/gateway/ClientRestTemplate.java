package com.prosource.siteminder.email.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ClientRestTemplate {

    private final RestTemplate emailRestTemplate;

    public <T, R> ResponseEntity<R> sendPost(String url, HttpEntity<T> httpEntity, Class<R> responseType) {
        return emailRestTemplate.postForEntity(url,
                httpEntity,
                responseType);
    }
}
