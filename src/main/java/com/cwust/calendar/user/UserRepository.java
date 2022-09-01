package com.cwust.calendar.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cwust.calendar.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Transactional(readOnly = true)
	User findByUsername(String username);
}
