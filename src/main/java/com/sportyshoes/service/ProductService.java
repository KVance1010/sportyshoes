package com.sportyshoes.service;


import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sportyshoes.dao.ProductRepository;
import com.sportyshoes.exceptions.ProductNotFoundException;
import com.sportyshoes.model.Product;

@Service
@Transactional
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 5;

	@Autowired
	private ProductRepository userRepo;
	
	public List<Product> listAllProducts() {
		return (List<Product>) userRepo.findAll(Sort.by("category").ascending());
	}
	
	public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);
		if (keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		return userRepo.findAll(pageable);
	}

	public Product save(Product product) {
		return userRepo.save(product);
	}

	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Could not find any Product with ID " + id);
		}
	}

	public String checkUnique(Integer id, String name) {
		Product ProductByName = userRepo.findByName(name);
		
		if (ProductByName == null)
			return "OK";

		boolean isCreatingNew = (id == null || id == 0);
		
		if (isCreatingNew) {
			if (ProductByName != null) return "DuplicateName";
			}else {
			if (ProductByName.getId() != id) {
				return "DuplicateName";}
			}
		return "OK";
	}

	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}

	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Could not find any Product with ID " + id);
		}

		userRepo.deleteById(id);
	}
}
