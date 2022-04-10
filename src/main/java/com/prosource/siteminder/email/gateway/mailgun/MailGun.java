package com.prosource.siteminder.email.gateway.mailgun;

import com.prosource.siteminder.email.config.EmailProperties;
import com.prosource.siteminder.email.dto.EmailRequest;
import com.prosource.siteminder.email.dto.EmailSenderRequest;
import com.prosource.siteminder.email.exception.RetryableException;
import com.prosource.siteminder.email.gateway.ClientRestTemplate;
import com.prosource.siteminder.email.gateway.EmailGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.LinkedMultiValueMap;

import static com.prosource.siteminder.email.util.EmailUtil.flatten;

@RequiredArgsConstructor
@Slf4j
@Retryable(value = RetryableException.class,
        maxAttemptsExpression = "${email.retry.max-attempts}",
        backoff = @Backoff(delayExpression = "${email.retry.back-off.initial-delay-ms}",
                maxDelayExpression = "${email.retry.back-off.max-delay-ms}",
                multiplierExpression = "${email.retry.back-off.multiplier}"))
public class MailGun implements EmailGateway {

    private final EmailProperties.GatewayDetails properties;
    private final ClientRestTemplate client;

    @Override
    public String getName() {
        return "MailGun";
    }

    @Override
    public ResponseEntity<String> send(EmailRequest emailRequest) {
        EmailSenderRequest request = map(emailRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("api", properties.getApiKey());

        LinkedMultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("from", request.getFrom());
        queryParam.add("to", request.getTo());
        queryParam.add("subject", request.getSubject());
        queryParam.add("text", request.getText());
        try {
            return client.sendPost(properties.getUrl(), new HttpEntity<>(queryParam, headers), String.class);
        } catch (RetryableException e) {
            log.error("Encountered a 5xx status. Retrying to send...");
            throw e;
        }
    }

    private EmailSenderRequest map(EmailRequest emailRequest) {
        return EmailSenderRequest.builder()
                .from(properties.getFrom())
                .to(flatten(emailRequest.getRecipients()))
                .cc(flatten(emailRequest.getCcs()))
                .bcc(flatten(emailRequest.getBccs()))
                .subject(emailRequest.getSubject())
                .text(emailRequest.getBody().getText())
                .build();
    }
}
