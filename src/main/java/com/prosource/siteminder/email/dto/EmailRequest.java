package com.prosource.siteminder.email.dto;

import com.prosource.siteminder.email.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EmailRequest {

    @NotNull
    @ValidEmail
    private List<String> recipients;
    @ValidEmail
    private List<String> ccs;
    @ValidEmail
    private List<String> bccs;
    private String subject;
    private EmailBody body;

    @Data
    public static class EmailBody {
        private String text;
        private String html;
    }

}
