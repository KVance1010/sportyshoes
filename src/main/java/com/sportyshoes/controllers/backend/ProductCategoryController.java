package com.sportyshoes.controllers.backend;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sportyshoes.exceptions.ProductNotFoundException;
import com.sportyshoes.model.Product;
import com.sportyshoes.service.CategoryService;
import com.sportyshoes.service.FileUploadUtil;
import com.sportyshoes.service.ProductService;

@Controller
public class ProductCategoryController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
		
	@GetMapping("/products")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "id", "asc", null);
	}
	
	@GetMapping("/products/page/{pageNum}") 
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
		
		return "/products/products";		
	}
	
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		List<Product> listProducts = productService.listAllProducts();
		
		model.addAttribute("product", new Product());
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("categoryList", categoryService.findAllCategories());
		model.addAttribute("pageTitle", "Create New Product");
		
		return "/products/product_form";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(Product product, 
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setImage(fileName);

			Product savedProduct = productService.save(product);
			String uploadDir = "product-images/" + savedProduct.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (product.getImage().isEmpty()) product.setImage(null);
			productService.save(product);
		}
		ra.addFlashAttribute("message", "The product has been saved successfully.");
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Product product = productService.get(id);
			
			model.addAttribute("product", product);
			model.addAttribute("categoryList", categoryService.findAllCategories());
			model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
			
			return "/products/product_form";			
		} catch (ProductNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/products";
		}
	}
	
	@GetMapping("/products/{id}/enabled/{status}")
	public String updateProductEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		productService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The product ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/products";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			productService.delete(id);
			String productDir = "product-images/" + id;
			FileUploadUtil.removeDir(productDir);
			
			redirectAttributes.addFlashAttribute("message", 
					"The product ID " + id + " has been deleted successfully");
		} catch (ProductNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/products";
	}
	
}

