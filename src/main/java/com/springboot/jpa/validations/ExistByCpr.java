package com.springboot.jpa.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistByCprValidation.class)
public @interface ExistByCpr {
    String message() default "El campo cpr ya existe en la base de datos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
