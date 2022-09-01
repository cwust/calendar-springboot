package com.cwust.calendar.user.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserLoginValidator implements ConstraintValidator<UserLoginConstraint, String> {
	public static final Pattern USER_LOGIN_PATTERN = Pattern.compile("\\[a-zA-Z]\\w[6,]");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return value == null || !USER_LOGIN_PATTERN.matcher(value).matches();
	}
}

