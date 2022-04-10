package com.prosource.siteminder.email.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EmailUtil {

    public static String flatten(List<String> recipients) {
        if (recipients == null) return null;
        return String.join(",", recipients);
    }

    public static <T> String toJson(T o) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(o);
    }
}
