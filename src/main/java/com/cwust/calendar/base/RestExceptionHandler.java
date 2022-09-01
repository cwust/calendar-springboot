package com.cwust.calendar.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cwust.calendar.base.dto.ErrorDTO;
import com.cwust.calendar.base.exception.CalendarException;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(CalendarException.class)
	protected ResponseEntity<?> handleCalendarException(CalendarException ex) {
		return ResponseEntity
				.status(ex.getHttpStatus())
				.body(new ErrorDTO(
						ex.getErrorCode(), 
						ex.getMessage(), 
						ex.getDetails()));
	}
}
