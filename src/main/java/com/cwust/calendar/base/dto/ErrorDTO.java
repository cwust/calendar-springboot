package com.cwust.calendar.base.dto;

public class ErrorDTO {
	private int code;
	private String message;
	private String details;

	public ErrorDTO(int code, String message, String details) {
		super();
		this.code = code;
		this.message = message;
		this.details = details;
	}

	public ErrorDTO(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
