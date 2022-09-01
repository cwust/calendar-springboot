package com.cwust.calendar.user.enums;

public enum CalendarAuthority {
	ADMIN(true), USER(false);

	private boolean admin;

	private CalendarAuthority(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

}
