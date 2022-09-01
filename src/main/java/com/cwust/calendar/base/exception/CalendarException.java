package com.cwust.calendar.base.exception;

import org.springframework.http.HttpStatus;

public class CalendarException extends RuntimeException {

	private static final long serialVersionUID = 1943890202067168194L;

	private CalendarError calendarError;
	private String details;

	public CalendarException(CalendarError calendarError, String details, Throwable cause) {
		super(calendarError.getMessage(), cause);
		this.calendarError = calendarError;
	}

	public CalendarException(CalendarError calendarError, String details) {
		this(calendarError, details, null);
	}

	public CalendarException(CalendarError calendarError, Throwable cause) {
		this(calendarError, null, cause);
	}

	public CalendarException(CalendarError calendarError) {
		this(calendarError, null, null);
	}

	public HttpStatus getHttpStatus() {
		return this.getCalendarError().getHttpStatus();
	}

	public int getErrorCode() {
		return this.getCalendarError().getCode();
	}

	public String getDetails() {
		return details;
	}

	public CalendarError getCalendarError() {
		return calendarError;
	}
}
