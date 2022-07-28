package com.sportyshoes.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sportyshoes.model.CartItem;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;

public interface CartRepository extends JpaRepository<CartItem, Integer> {

	public List<CartItem> findByUser(User user);

	public CartItem findByUserAndProduct(User user, Product product);

	@Modifying
	@Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.user.id = ?2 and c.product.id = ?3")
	public void updateQuantity(Integer quantity, Integer userId, Integer productId);

	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.user.id = ?1 and c.product.id = ?2")
	public void deleteByUserAndProduct(Integer userId, Integer productId);

	@Modifying
	@Query("DELETE CartItem c WHERE c.user.id = ?1")
	public void deleteByUser(Integer userId);


}
