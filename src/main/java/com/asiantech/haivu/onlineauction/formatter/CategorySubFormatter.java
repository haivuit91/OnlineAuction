package com.asiantech.haivu.onlineauction.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.service.CategorySubService;

public class CategorySubFormatter implements Formatter<CategorySub> {

	@Autowired
	private CategorySubService categorySubService;

	public CategorySubFormatter() {
		super();
	}

	@Override
	public String print(CategorySub object, Locale locale) {
		return (object != null ? String.valueOf(object.getId()) : "");
	}

	@Override
	public CategorySub parse(String text, Locale locale) throws ParseException {
		Integer categorySubId = Integer.valueOf(text);
		return this.categorySubService.findCategorySubbyId(categorySubId);
	}

}
