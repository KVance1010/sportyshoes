package com.sportyshoes.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

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
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repo;

	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Shoes");
		Category savedCategory = repo.save(category);
		Category category1 = new Category("Socks");
		Category savedCategory1 = repo.save(category1);
		Category category2 = new Category("Accessories");
		Category savedCategory2 = repo.save(category2);

		assertThat(savedCategory.getId()).isGreaterThan(0);
		assertThat(savedCategory1.getId()).isGreaterThan(0);
		assertThat(savedCategory2.getId()).isGreaterThan(0);
	}

	@Test
	public void testSubCategory() {
		Category parent = new Category(1);
		Category subCategory = new Category("Men", parent);
		Category savedCategory = repo.save(subCategory);
		Category subCategory1 = new Category("Women", parent);
		Category savedCategory1 = repo.save(subCategory1);
		Category subCategory2 = new Category("Unisex", parent);
		Category savedCategory2 = repo.save(subCategory2);

		assertThat(savedCategory.getId()).isGreaterThan(0);
		assertThat(savedCategory1.getId()).isGreaterThan(0);
		assertThat(savedCategory2.getId()).isGreaterThan(0);
	}

	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = repo.findAll();
		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());
				Set<Category> children = category.getChildren();
				for(Category subCategory : children) {
					System.out.println("--"+ subCategory.getName());
					printChilderen(subCategory,1);
				}
			}
		}
	}
	
	private void printChilderen(Category parent, int subLevel) {
		int newSubLevel = subLevel +1;
		Set<Category> children = parent.getChildren();
		for (Category subCategory :children) {
			for (int i = 0; i < newSubLevel; i++) {
			System.out.println("--");
		}
			System.out.println(subCategory.getName());
		}
		
	}
	
	
	
	
	
	
	
	
	
}
