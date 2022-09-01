package com.cwust.calendar.base.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import com.cwust.calendar.user.entity.User;

@MappedSuperclass
public class GenericUpdateableEntity extends GenericCreateableEntity {
	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id")
	private User updateUser;

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	@PreUpdate
	public void preUpdate() {
		//TODO: get current user
		this.updateDate = LocalDateTime.now();
	}
}
