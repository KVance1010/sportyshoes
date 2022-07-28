package com.sportyshoes.controllers.frontend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.exceptions.ShoppingCartException;
import com.sportyshoes.model.User;
import com.sportyshoes.service.UserService;
import com.sportyshoes.service.cartService;

@RestController
public class CartRestController {
	@Autowired private cartService cartService;
	//@Autowired private CustomerDetails customer;
	@Autowired private UserService userService;

	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {

		try {
			User customer = getCustomer(request);
			Integer updatedQuantity = cartService.addProduct(productId, quantity, customer);
			return updatedQuantity + " item(s) of this product were added to your shopping cart.";
		} catch (ShoppingCartException ex) {
			return ex.getMessage();
		}
	}

	private User getCustomer(HttpServletRequest request) {
		String email = request.getRemoteUser();   
		return userService.getByEmail(email);
	}

	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		User customer =   getCustomer(request);
		float subtotal = cartService.updateQuantity(productId, quantity, customer);
		return String.valueOf(subtotal);
	}

	@DeleteMapping("/cart/remove/{productId}")
	public String removeProduct(@PathVariable("productId") Integer productId, HttpServletRequest request) {
		User customer = getCustomer(request);
		cartService.removeProduct(productId, customer);
		return "The product has been removed from your shopping cart.";
	}
}
