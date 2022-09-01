package com.cwust.calendar.user.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
	public static final Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]");
	public static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
	public static final Pattern SYMBOL_PATTERN = Pattern.compile("\\W");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		return getNumberOfOcurrences(LETTER_PATTERN, value) > 0 &&
				getNumberOfOcurrences(DIGIT_PATTERN, value) > 0 &&
				getNumberOfOcurrences(SYMBOL_PATTERN, value) > 0 &&
				value.length() >= 6;
	}

	private int getNumberOfOcurrences(Pattern pattern, String str) {
		return (int) pattern.matcher(str).results().count();
	}
}