package com.himax.ead.authuser.core.validation.nullsizeable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NullSizeableValidator.class })
public @interface NullSizeable {
    String message() default " {0} should not be null, empty and should have values between {2} and {1}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int min();
    int max();
}
