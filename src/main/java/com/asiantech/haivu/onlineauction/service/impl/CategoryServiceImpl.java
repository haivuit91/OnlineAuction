package com.asiantech.haivu.onlineauction.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.common.PageAbleCommon;
import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.repository.CategoryRepository;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;

@Service(CategoryService.NAME)
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryRepository cateRepository;
	
	@Autowired
	private CategorySubService categorySubService;

	@Override
	public List<Category> findAllCategory() {
		return cateRepository.findAll();
	}

	@Override
	public Page<Category> findCategoryUsingPage(Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return cateRepository.findAll(page);
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
	public boolean deleteCategory(long id) {
		boolean check = false;
		Category cate = cateRepository.findOne(id);
		if(cate != null) {
			List<CategorySub> cateSub = categorySubService.findCategorySubByCategory(cate);
			if(!cateSub.isEmpty()) {
				if (categorySubService.deleteCategorySubByCategory(cate)) {
					cateRepository.delete(cate);
					check = true;
				}
			} else {
				cateRepository.delete(cate);
				check = true;
			}
		}
		return check;
	}

}