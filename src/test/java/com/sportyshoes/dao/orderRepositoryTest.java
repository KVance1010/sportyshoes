package com.sportyshoes.dao;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sportyshoes.model.Order;
import com.sportyshoes.model.OrderItem;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class orderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test // save order along with the order items
	void saveOrderMetod() {

		Order order = new Order();
		order.setStatus("In progress");
		order.setOrderTrackingNumber("sdkfjal");

		// create order item 1
		OrderItem orderItem1 = new OrderItem();
		orderItem1.setProduct(productRepository.findById(1).get());
		orderItem1.setQuantity(2);
		orderItem1.setPrice(orderItem1.getProduct().getPrice().multiply(new BigDecimal(orderItem1.getQuantity())));
		order.getOrderItems().add(orderItem1);

		// create order item 2
		OrderItem orderItem2 = new OrderItem();
		orderItem2.setProduct(productRepository.findById(5).get());
		orderItem2.setQuantity(3);
		orderItem2.setPrice(orderItem2.getProduct().getPrice().multiply(new BigDecimal(orderItem2.getQuantity())));
		order.getOrderItems().add(orderItem2);
		order.setTotalPrice(order.getTotalAmoung());
		
		
		order.setTotalQuantity(2);
		
		orderRepository.save(order);
	}

	@Test
	void fetchOrderMethod() {
		Order order = orderRepository.findById(1L).get();
		System.out.println(order.getStatus());
		System.out.println(order.toString());
		for (OrderItem item: order.getOrderItems()) {
			System.out.println(item.getProduct().getName());
		}
	}
	
	@Test
	void deleteOrderMethod() {
		orderRepository.deleteById(1L);
	}
}
