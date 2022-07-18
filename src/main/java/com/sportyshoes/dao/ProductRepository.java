package com.sportyshoes.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sportyshoes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
		
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);
	
	public Long countById(Integer id);
	
	public Product findByName(String name);
	
	public Product findByCategory(String category);
	
	@Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);	
}

