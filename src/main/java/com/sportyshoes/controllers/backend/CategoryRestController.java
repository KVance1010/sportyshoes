package com.sportyshoes.controllers.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.service.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService service;
	
	@PostMapping("/categories/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name,
			@Param("url") String url) {
		return service.checkUnique(id, name, url);
	}
}
