package com.sportyshoes.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sportyshoes.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);

	public Long countById(Integer id);

	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' '," + " u.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
//	@Query("SELECT u FROM User u LEFT JOIN u.roles r WHERE r.id = :id AND CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' '," + " u.lastName) LIKE %?1% ")
//	public Page<User> findAll(Optional<String> keyword , Pageable pageable, Integer id);
	
	@Query("SELECT u FROM User u LEFT JOIN u.roles r WHERE r.id = :id")
	public Page<User> findAll(Pageable pageable, Integer id);
}
