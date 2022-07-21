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
		Category category = entityManager.find(Category.class, 1);
		Product product = new Product();
		product.setDescription("mens Running Shoes");
		product.setEnabled(true);
		product.setName("Mens Running Shoes");
		product.setPrice(new BigDecimal(150.0));
		product.setCategory(category);
		prodRepo.save(product);

		Product product1 = new Product();
		product1.setDescription("Non Slip Work Shoes");
		product1.setEnabled(true);
		product1.setName("Mens Work Shoes");
		product1.setPrice(new BigDecimal(250.0));
		product1.setCategory(category);
		prodRepo.save(product1);

	}

	@Test
	public void testNewproduct() {
		Category category = entityManager.find(Category.class, 3);
		System.out.println(category.getCategoryName());
		Product product = new Product();
		product.setDescription("Colorfull socks");
		product.setEnabled(true);
		product.setName("Colorfull socks");
		product.setImage(null);
		product.setPrice(new BigDecimal(15.0));
	    product.setCategory(category);
		prodRepo.save(product);

//		category.addProduct(product);
//		catRepo.save(category);
	}

	@Test
	public void testListAllProducts() {
		Iterable<Product> prod = prodRepo.findAll(Sort.by("category").ascending());
		prod.forEach(product -> System.out.println(product));
	}
}
