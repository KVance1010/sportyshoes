package com.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.dao.CategoryRepository;
import com.sportyshoes.model.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepo;
	
	public Category findCategoryById(Integer id) {
		Category category = catRepo.findById(id).get();
		return category;
	}
	
	public List<Category> findAllCategories(){
		List <Category> categories = catRepo.findAll();
		return categories;
	}
	
	public void deleteCategoryById(Integer id) {
		catRepo.deleteById(id);
	}
	
	public void saveCategory(Category category) {
		catRepo.save(category);
	}
}
