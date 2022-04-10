package com.prosource.siteminder.email.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {

    private String id;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorResponse> errors;
}
