package com.sportyshoes.dao;


import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepoitoryTest {

	@Autowired
	private ProductRepository prodRepo;
	private CategoryRepository catRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void testDeleteById() {
		Category cat = entityManager.find(Category.class, 1);
		System.out.println(cat.getId());
		catRepo.deleteById(1);
	}
	
	@Test
	public void testCreateNewproduct() {
		Category cat = entityManager.find(Category.class, 1);
		//Category cat = new Category("Mens Shoes");
	//	Product product = new Product();
//		product.setDescription("mens Running Shoes");
//		product.setEnabled(true);
//		product.setName("Mens Running Shoes");
//		product.setPrice(new BigDecimal(150.0));
//		product.setCategory(cat);
//		prodRepo.save(product);
		
		Product product1 = new Product();
		product1.setDescription("Mens Dress Shoes");
		product1.setEnabled(true);
		product1.setName("Mens Dress Shoes");
		product1.setPrice(new BigDecimal(250.0));
		product1.setCategory(cat);
		prodRepo.save(product1);

	}
	
	@Test
	public void testListAllProducts() {
		Iterable<Product> prod= prodRepo.findAll(Sort.by("category").ascending());
		prod.forEach(product -> System.out.println(product));
	}
}
