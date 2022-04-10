package com.prosource.siteminder.email.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EmailValidator implements ConstraintValidator<ValidEmail, List<String>> {

    private final org.apache.commons.validator.routines.EmailValidator validator
            = org.apache.commons.validator.routines.EmailValidator.getInstance();

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.stream().allMatch(validator::isValid);
    }
}
