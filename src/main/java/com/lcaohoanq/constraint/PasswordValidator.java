package com.lcaohoanq.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false; // Not null constraint
        }
        if (password.isEmpty()) {
            return true; // Accept blank password
        }
        return password.matches(PASSWORD_PATTERN);
    }
}
