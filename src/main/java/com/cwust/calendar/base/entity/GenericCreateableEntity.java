package com.cwust.calendar.base.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Where;

import com.cwust.calendar.user.entity.User;

@MappedSuperclass
@Where(clause = "delete_date is null")
public class GenericCreateableEntity extends GenericEntity {
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id")
	private User createUser;

	@Column(name = "delete_date")
	private LocalDateTime deleteDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delete_user_id")
	private User deleteUser;
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	
	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(LocalDateTime deleteDate) {
		this.deleteDate = deleteDate;
	}

	public User getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(User deleteUser) {
		this.deleteUser = deleteUser;
	}

	@PrePersist
	public void prePersist() {
		//TODO: get current user
		this.createDate = LocalDateTime.now();
	}
}
