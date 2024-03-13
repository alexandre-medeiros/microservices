package com.himax.ead.authuser.core.validation.nullsizeable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullSizeableValidator implements ConstraintValidator<NullSizeable, String> {
    private int min;
    private int max;

    @Override
    public void initialize(NullSizeable constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank() || value.isEmpty()){
            return false;
        }

        int number = value.length();
        return number >= this.min && number <= this.max;
    }
}
