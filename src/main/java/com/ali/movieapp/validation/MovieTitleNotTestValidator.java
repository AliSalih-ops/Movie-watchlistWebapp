package com.ali.movieapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MovieTitleNotTestValidator implements ConstraintValidator<MovieTitleNotTest, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.equalsIgnoreCase("test");
    }
}
