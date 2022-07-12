package com.sportyshoes.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.sportyshoes.model.Role;
import com.sportyshoes.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepoitoryTest {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userName = new User("vance@gmail.com", "password", "Kyle", "Vance");
		userName.addRole(roleAdmin);
		
		User savedUser = repo.save(userName);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userName = new User("jsmith@gmail.com", "password", "John", "Smith");
		Role roleManager = new Role(4);
		Role roleShipper = new Role(3);
		userName.addRole(roleManager);
		userName.addRole(roleShipper);
		User savedUser = repo.save(userName);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User user =repo.findById(1).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User user = repo.findById(1).get();
		user.setEnabled(true);
		user.setEmail("kvance@gmail.com");
		
		repo.save(user);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User user = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		user.getRoles().remove(roleEditor);
		user.addRole(roleSalesperson);
		
		repo.save(user);
	}
	
	@Test
	public void deleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);	
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "js@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDiableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	@Test
	public void testEnableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}
}
