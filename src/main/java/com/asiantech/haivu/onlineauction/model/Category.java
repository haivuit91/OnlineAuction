package com.asiantech.haivu.onlineauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category")
public class Category extends Model {

	@Column(name = "cate_name", length = 100)
	@NotNull(message = "The category name is required and can't be empty")
	private String cateName;

	public Category() {
		super();
	}

	public Category(String cateName) {
		super();
		this.cateName = cateName;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

}
