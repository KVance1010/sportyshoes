package com.sportyshoes.controllers.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.service.CategoryService;
import com.sportyshoes.service.ProductService;

@Controller
public class StoreController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/store")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "id", "asc", null);
	}

	@GetMapping("/store/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<Product> page = productService.listByPage(pageNum, sortField, sortDir, keyword);
		List<Product> listProducts = page.getContent();

		long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("categoryList", categoryService.findAllCategories());
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "store/store";
	}

	@GetMapping("/store/searchbycategory")
	public String getByCategory(@Param("id") Integer id, Model model) {
		Category category = categoryService.findCategoryById(id);
		model.addAttribute("listProducts", category.getProducts());
		model.addAttribute("categoryName", category.getCategoryName());
		model.addAttribute("categoryList", categoryService.findAllCategories());
		return "/store/store";

	}

}
