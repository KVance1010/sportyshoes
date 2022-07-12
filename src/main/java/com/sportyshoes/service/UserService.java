package com.sportyshoes.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sportyshoes.dao.RoleRepository;
import com.sportyshoes.dao.UserRepository;
import com.sportyshoes.exceptions.UserNotFoundException;
import com.sportyshoes.model.Role;
import com.sportyshoes.model.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List <User> listAllUsers(){
		return (List<User>) userRepo.findAll();
	}
	
	public void saveUser(User user) {
		boolean isUpdatingUser = (user.getId() == null);
		
		if(isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}
		}else {
			encodePassword(user);
		}
		userRepo.save(user);
	}
	
	public List<Role> listAllRoles(){
		return (List<Role>) roleRepo.findAll();
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if (userByEmail == null) return true;
		boolean isCreatingNew = (id == null);
		if(isCreatingNew) {
			if (userByEmail != null) return false;
		}else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}

	public User getUserById(Integer id) throws UserNotFoundException{
		try {
		return userRepo.findById(id).get();
		
		}catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with the ID: " + id);
		}
	}
	
	public void deleteById(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with the ID: " + id);
		}
		userRepo.deleteById(id);
	}
	
	public void updateUserEnableStatus(Integer id, boolean enabled ) {
		userRepo.updateEnabledStatus(id, enabled);
	}
	
}
