package com.prosource.siteminder.email.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = { EmailValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface ValidEmail {
    String message() default "Email is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
