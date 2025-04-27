package com.ali.movieapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MovieTitleNotTestValidator.class)
public @interface MovieTitleNotTest {
    String message() default "{movie.title.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
