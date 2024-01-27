package com.himax.ead.authuser.core.validation.cpf;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class CPFConstraints implements ConstraintValidator<CPF,String> {

    @Override
    public void initialize(CPF constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        CpfValidador builder = new CpfValidador();
        return builder.isCPF(value);
    }
}
