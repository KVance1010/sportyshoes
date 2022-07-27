package com.sportyshoes.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table (name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "order_tracking_number", nullable = false, unique = true) 
	private String orderTrackingNumber;
	@Column(name = "order_total_quantity", nullable = false)
	private int totalQuantity;
	@Column(name = "order_total_price", nullable = false)
	private BigDecimal totalPrice;
	@Column(name = "order_status")
	private String status;
	@Column(name = "order_creation_date")
	@CreationTimestamp
	private LocalDate dateCreated;
	@Column(name = "order_last_update")
	@UpdateTimestamp
    private LocalDate lastUpdated;
	
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
	private Set<OrderItem> orderItems = new HashSet<>();
	
	
	public BigDecimal getTotalAmoung() {
		BigDecimal amount = new BigDecimal(0.00);
		for (OrderItem item : this.orderItems) {
			amount = amount.add(item.getPrice());
		}
		return amount;
	}
}
