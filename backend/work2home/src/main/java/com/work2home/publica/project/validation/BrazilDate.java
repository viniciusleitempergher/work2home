package com.work2home.publica.project.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = BrazilDateValidator.class)
public @interface BrazilDate {

	String message() default "Data está incorreta";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
