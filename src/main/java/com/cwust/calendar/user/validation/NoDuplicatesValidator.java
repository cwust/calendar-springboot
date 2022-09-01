package com.cwust.calendar.user.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoDuplicatesValidator implements ConstraintValidator<NoDuplicatesConstraint, List<String>> {

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		Set<?> set = new HashSet<>(value);
		return set.size() == value.size();		
	}
}