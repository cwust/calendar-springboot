package com.cwust.calendar.user.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ValidCalendarAuthorityValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCalendarAuthorityConstraint {
	String message() default "Invalid authority";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
