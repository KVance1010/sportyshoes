package com.sportyshoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 40, nullable = false, unique = true)
	private String name;
	@Column(length = 150, nullable = false)
	private String description;
		
	public Role(String name) {
		super();
		this.name = name;
	}
	
	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Role(Integer id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
		
}
