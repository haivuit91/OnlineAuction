package com.asiantech.haivu.onlineauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category_sub")
public class CategorySub {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 1000)
	private long id;

	@Column(name = "category_sub_name")
	private String cateSubName;

	@Column(name = "category_path")
	private String cateSubPath;

	@ManyToOne
	private Category category;

	public CategorySub() {
		super();
	}

	public CategorySub(long id, String cateSubName, String cateSubPath, Category category) {
		super();
		this.id = id;
		this.cateSubName = cateSubName;
		this.cateSubPath = cateSubPath;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
