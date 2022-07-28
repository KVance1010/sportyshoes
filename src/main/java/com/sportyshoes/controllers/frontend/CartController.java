package com.sportyshoes.controllers.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sportyshoes.model.CartItem;
import com.sportyshoes.model.User;
import com.sportyshoes.service.cartService;

@Controller
public class CartController {

	@Autowired
	private cartService cartService;
	

	@GetMapping("/cart")
	public String viewCart(Model model, User user) {

		List<CartItem> cartItems = cartService.listCartItems(user);

		float estimatedTotal = 0.0F;

//		for (CartItem item : cartItems) {
//			estimatedTotal += item.getSubtotal();
//		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);

		return "/checkout/cart-details";
	}

	
}
