package com.sportyshoes.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 128, nullable = false, unique = true)
	private String name;

	@Column(length = 64, nullable = false)
	private String category;
	
	@Column(nullable = false)
	private String description;

	@Column(length = 128)
	private String image;

	private boolean enabled;
	
	@Column(nullable = false)
	private BigDecimal price;

	public Product() {
	}

	public Product(Integer id) {
		this.id = id;
	}

	public Product(String name, String category, String description, String image, boolean enabled, BigDecimal price) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.image = image;
		this.enabled = enabled;
		this.price = price;
	}

	public Product(String name, String category, String description, BigDecimal price) {
		this.name = name;
		this.category = category;
		this.description= description;
		this.price = price;
		this.image = "image-thumbnail.png";
		this.enabled = true;
	}

	public Integer getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public String toString() {
		return "Product [name=" + name + ", category=" + category + ", description=" + description + ", image=" + image
				+ ", enabled=" + enabled + ", price=" + price + "]";
	}

	@Transient
	public String getImagePath() {
		if (this.id == null)
			return "/images/image-thumbnail.png";

		return "/product-images/" + this.id + "/" + this.image;
	}

}