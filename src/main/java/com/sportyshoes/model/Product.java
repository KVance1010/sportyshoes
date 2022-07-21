package com.sportyshoes.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 128, nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private String description;

	@Column(length = 128)
	private String image;

	private boolean enabled;
	
	@Column(nullable = false)
	private BigDecimal price;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name= "category_id", referencedColumnName = "id")
	private Category category;

	public Product(String name, Category category, String description, String image, boolean enabled, BigDecimal price) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.image = image;
		this.enabled = enabled;
		this.price = price;
	}

	@Transient
	public String getImagePath() {
		if (this.id == null)
			return "/images/image-thumbnail.png";

		return "/product-images/" + this.id + "/" + this.image;
	}

}