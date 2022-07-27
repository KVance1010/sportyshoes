package com.sportyshoes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sportyshoes.dao.OrderRepository;
import com.sportyshoes.model.Order;

@Service
@Transactional
public class OrderService  {

	@Autowired
	private OrderRepository orderRepository;

	public void saveOrder(Order order) {
		orderRepository.save(order);
	}
	
	public List<Order> listAllOrders() {
		return (List<Order>) orderRepository.findAll(Sort.by("id").ascending());
	}
	
	

}
