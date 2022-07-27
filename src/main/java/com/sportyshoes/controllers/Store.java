package com.sportyshoes.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Order;
import com.sportyshoes.model.Product;
import com.sportyshoes.service.CategoryService;
import com.sportyshoes.service.OrderService;
import com.sportyshoes.service.ProductService;

@Controller
public class Store {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrderService orderService;
		
	@GetMapping("/store")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "id", "asc", null);
	}
	
	@GetMapping("/store/page/{pageNum}") 
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword
			){
				
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
		
		return "store";		
	}
	
	@GetMapping("/store/searchbycategory")
	public String getByCategory(@Param("id") Integer id, Model model) {
		Category category = categoryService.findCategoryById(id);
		model.addAttribute("listProducts", category.getProducts());
		model.addAttribute("categoryName", category.getCategoryName());
		model.addAttribute("categoryList", categoryService.findAllCategories());
		return "store";
		
	}
	
	//**************** TODO***********************************
	@PostMapping("/store/addtocart/{id})")
	public String addProduct(Product product, Model model, @PathVariable(name = "id") int id) throws IOException {

		
		
		return "redirect:/store";
	}
	
	@GetMapping("/store/cart")
	public String cart() {
		
		
		return"/checkout/cart-details";
	}
	
	@PostMapping("/checkout")
	public String checkout(Order order, RedirectAttributes redirect) {	
		orderService.saveOrder(order);
		redirect.addFlashAttribute("message", "Order placed successfully.");
		return"redirect:/store";
	}

}
