package com.asiantech.haivu.onlineauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category_sub")
public class CategorySub extends Model {

	@Column(name = "category_sub_name")
	private String cateSubName;

	@Column(name = "category_path")
	private String cateSubPath;

	@ManyToOne
	private Category category;

	public CategorySub() {
		super();
	}

	public CategorySub(String cateSubName, String cateSubPath, Category category) {
		super();
		this.cateSubName = cateSubName;
		this.cateSubPath = cateSubPath;
		this.category = category;
	}

	public String getCateSubName() {
		return cateSubName;
	}

	public void setCateSubName(String cateSubName) {
		this.cateSubName = cateSubName;
	}

	public String getCateSubPath() {
		return cateSubPath;
	}

	public void setCateSubPath(String cateSubPath) {
		this.cateSubPath = cateSubPath;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
