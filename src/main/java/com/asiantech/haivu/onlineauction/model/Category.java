package com.asiantech.haivu.onlineauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 1000)
	private long id;

	@Column(name = "cate_name", length = 100)
	@NotNull(message = "The category name is required and can't be empty")
	private String cateName;

	public Category() {
		super();
	}

	public Category(long id, String cateName) {
		super();
		this.id = id;
		this.cateName = cateName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

}
