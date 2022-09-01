package com.cwust.calendar.base.exception;

import org.springframework.http.HttpStatus;

public enum CalendarError {
	VALIDATION_ERROR(1, HttpStatus.BAD_REQUEST, "Error validating data"),
	UNAUTHORIZED(2, HttpStatus.FORBIDDEN, "Unauthorized"),
	INVALID_LOGIN(3, HttpStatus.FORBIDDEN, "Invalid login or password"),
	LOGIN_UNAVAILABLE(1001, HttpStatus.UNPROCESSABLE_ENTITY, "This login is unavailable");
	

	private int code;
	private HttpStatus httpStatus;
	private String message;

	private CalendarError(int code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void throwException() {
		throw new CalendarException(this);
	}

	public void throwException(String details) {
		throw new CalendarException(this, details);
	}
	
	public void throwException(Throwable cause) {
		throw new CalendarException(this, cause);
	}
	
}
