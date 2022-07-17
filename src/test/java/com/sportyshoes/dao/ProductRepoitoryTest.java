package com.sportyshoes.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.sportyshoes.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepoitoryTest {

	@Autowired
	private ProductRepository repo;
	
	
	@Test
	public void testCreateNewUserWithOneRole() {
		
	}
	
	@Test
	public void testCreateNewproduct() {
		Product product = new Product("Mens Running Shoe", "mens shoes", "mens running shoe", new BigDecimal(150.0));
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct.getId()).isGreaterThan(0);

	}
	
	@Test
	public void testListAllProducts() {
		Iterable<Product> prod= repo.findAll(Sort.by("category").ascending());
		prod.forEach(product -> System.out.println(product));
	}
}
