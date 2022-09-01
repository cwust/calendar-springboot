package com.cwust.calendar.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {
	public Date dateNow() {
		return new Date();
	}
	
	public Date addSeconds(Date date, int seconds) {
		return Date.from(date.toInstant().plusSeconds(seconds));
	}
}
