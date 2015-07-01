package com.asiantech.haivu.onlineauction.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.repository.CategoryRepository;
import com.asiantech.haivu.onlineauction.service.CategoryService;

@Service(CategoryService.NAME)
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryRepository cateRepository;

	@Override
	public List<Category> findAllCategory() {
		return cateRepository.findAll();
	}

	@Override
	public Category findCategoryById(long id) {
		return cateRepository.findOne(id);
	}

	@Override
	@Transactional
	public Category saveCategory(Category category) {
		return cateRepository.save(category);
	}

	@Override
	@Transactional
	public Category deleteCategory(long id) {
		Category cate = cateRepository.findOne(id);
		cateRepository.delete(cate);
		return cate;
	}

}