package com.cwust.calendar.user.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cwust.calendar.user.enums.CalendarAuthority;

public class ValidCalendarAuthorityValidator implements ConstraintValidator<ValidCalendarAuthorityConstraint, List<String>> {

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		} else {
			return value.stream().allMatch(this::isValidAuthority);
		}
	}
	
	private boolean isValidAuthority(String authority) {
		try {
			return CalendarAuthority.valueOf(authority) != null;
		} catch (Exception e) {
			return false;
		}
	}
}