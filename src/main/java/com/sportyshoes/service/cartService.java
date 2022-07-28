package com.sportyshoes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.dao.CartRepository;
import com.sportyshoes.dao.ProductRepository;
import com.sportyshoes.exceptions.ShoppingCartException;
import com.sportyshoes.model.CartItem;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;

@Service
@Transactional
public class cartService {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private ProductRepository productRepo;
	

	public Integer addProduct(Integer productId, Integer quantity, User user) throws ShoppingCartException {
		Integer updatedQuantity = quantity;
		Product product = productRepo.findById(productId).get();

		CartItem cartItem = cartRepo.findByUserAndProduct(user, product);

		if (cartItem != null) {
			updatedQuantity = cartItem.getQuantity() + quantity;
			cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
		} else {
			cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
		}
		cartItem.setQuantity(updatedQuantity);
		cartRepo.save(cartItem);
		return updatedQuantity;
	}

	public List<CartItem> listCartItems(User user) {
		return cartRepo.findByUser(user);
	}

	public Float updateQuantity(Integer productId, Integer quantity, User user) {
		cartRepo.updateQuantity(quantity, user.getId(), productId);
		Product product = productRepo.findById(productId).get();
		float subtotal = product.getPrice() * quantity;
		return subtotal;
	}

	public void removeProduct(Integer productId, User user) {
		cartRepo.deleteByUserAndProduct(user.getId(), productId);
	}

	public void deleteByUser(User user) {
		cartRepo.deleteByUser(user.getId());
	}

}
