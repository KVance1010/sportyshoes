package com.sportyshoes.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sportyshoes.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepoitoryTest {

	@Autowired
	private CategoryRepository repo;
	
	
	@Test
	public void testCreateOne() {
		 Category cat1 = new Category("Mens Shoes");
		 Category savedProduct= repo.save(cat1);
		    assertThat(savedProduct.getId()).isGreaterThan(0);
		   System.out.println(cat1.getId());
	}
	
	@Test
	public void testCreateNewCategory() {
	  
	    Category cat2 = new Category("Womens Shoes");
	    Category cat3 = new Category("Accessories");
	    
	    Category savedProduct= repo.save(cat2);
	    assertThat(savedProduct.getId()).isGreaterThan(0);
	    repo.save(cat3);
	  
	}
	
	
	
	
}
