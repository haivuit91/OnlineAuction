package com.asiantech.haivu.onlineauction.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.service.CategoryService;

public class CategoryFormatter implements Formatter<Category> {
	
	@Autowired
	private CategoryService categoryService;
	
	public CategoryFormatter() {
		super();
	}

	@Override
	public String print(Category object, Locale locale) {
		return (object != null ? String.valueOf(object.getId()) : "");
	}

	@Override
	public Category parse(String text, Locale locale) throws ParseException {
		Integer categoryId = Integer.valueOf(text);
		return categoryService.findCategoryById(categoryId);
	}

}
