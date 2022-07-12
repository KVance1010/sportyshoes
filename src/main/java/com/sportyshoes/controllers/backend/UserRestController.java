package com.sportyshoes.controllers.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	UserService service;
	
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		return service.isEmailUnique(id, email)? "OK" : "Duplicated";
	}
}
