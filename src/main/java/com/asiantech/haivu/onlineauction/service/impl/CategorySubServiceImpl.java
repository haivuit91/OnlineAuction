package com.asiantech.haivu.onlineauction.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.repository.CategorySubRepository;
import com.asiantech.haivu.onlineauction.service.CategorySubService;

@Service(CategorySubService.NAME)
public class CategorySubServiceImpl implements CategorySubService {

	@Resource
	private CategorySubRepository cateSubRepository;

	@Override
	public List<CategorySub> findAllCategorySub() {
		return cateSubRepository.findAll();
	}

	@Override
	public CategorySub findCategorySubById(long id) {
		return cateSubRepository.findOne(id);
	}

	@Override
	public CategorySub findCategorySubByPath(String cateSubPath) {
		return cateSubRepository.findByCateSubPath(cateSubPath);
	}

	@Override
	@Transactional
	public CategorySub saveCategorySub(CategorySub categorySub) {
		return cateSubRepository.save(categorySub);
	}

	@Override
	@Transactional
	public CategorySub deleteCategorySub(long id) {
		CategorySub cateSub = cateSubRepository.findOne(id);
		cateSubRepository.delete(cateSub);
		return cateSub;
	}

}