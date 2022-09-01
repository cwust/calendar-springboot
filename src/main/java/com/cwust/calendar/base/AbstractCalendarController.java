package com.cwust.calendar.base;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.cwust.calendar.base.exception.CalendarError;

public abstract class AbstractCalendarController {
	public void validateBindingResult(BindingResult result) {
		if (result.hasErrors()) {
			String details = result.getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(","));
			
			CalendarError.VALIDATION_ERROR.throwException(details);			
		}
	}
}
