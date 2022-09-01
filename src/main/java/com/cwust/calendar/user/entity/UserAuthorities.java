package com.cwust.calendar.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.cwust.calendar.base.entity.GenericCreateableEntity;
import com.cwust.calendar.user.enums.CalendarAuthority;

@Entity
@Table(name = "user_authorities")
public class UserAuthorities extends GenericCreateableEntity {
	@Enumerated(EnumType.STRING)
	@Column(name = "authority", nullable = false)
	private CalendarAuthority authority;

	public UserAuthorities() {
		super();
	}

	public UserAuthorities(String authority) {
		super();
		this.authority = CalendarAuthority.valueOf(authority);
	}

	public CalendarAuthority getAuthority() {
		return authority;
	}

	public void setAuthority(CalendarAuthority authority) {
		this.authority = authority;
	}

}
