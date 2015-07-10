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
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.CategorySubRepository;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;

@Service(CategorySubService.NAME)
public class CategorySubServiceImpl implements CategorySubService {

	@Resource
	private CategorySubRepository cateSubRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ItemService itemService;

	@Override
	public List<CategorySub> findAllCategorySub() {
		return cateSubRepository.findAll();
	}

	@Override
	public Page<CategorySub> findCategorySubUsingPage(long categoryId, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		Category category = categoryService.findCategoryById(categoryId);
		return cateSubRepository.findByCategory(category, page);
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
	public List<CategorySub> findCategorySubByCategory(Category category) {
		return cateSubRepository.findByCategory(category);
	}

	@Override
	@Transactional
	public CategorySub saveCategorySub(CategorySub categorySub, long categoryId) {
		Category category = categoryService.findCategoryById(categoryId);
		CategorySub cateSub = new CategorySub(categorySub.getId(), categorySub.getCateSubName(), 
				categorySub.getCateSubPath(), category);
		return cateSubRepository.save(cateSub);
	}

	@Override
	@Transactional
	public boolean deleteCategorySub(long id) {
		boolean check = false;
		CategorySub cateSub = cateSubRepository.findOne(id);
		if(cateSub != null) {
			List<Item> item = itemService.findAllItemByCategorySub(cateSub);
			if(!item.isEmpty()) {
				if(itemService.deleteItemByCategorySub(cateSub)) {
					cateSubRepository.delete(cateSub);
					check = true;
				}
			} else {
				cateSubRepository.delete(cateSub);
				check = true;
			}
		}
		return check;
	}

	@Override
	@Transactional
	public boolean deleteCategorySubByCategory(Category category) {
		boolean check = false;
		if(category != null) {
			List<CategorySub> cateSub = cateSubRepository.findByCategory(category);
			if(!cateSub.isEmpty()) {
				for (CategorySub categorySub : cateSub) {
					List<Item> item = itemService.findAllItemByCategorySub(categorySub);
					if(!item.isEmpty()) {
						if(itemService.deleteItemByCategorySub(categorySub)) {
							if (cateSubRepository.deleteByCategory(category) != 0) {
								check = true;
							}
						}
					} else {
						if (cateSubRepository.deleteByCategory(category) != 0) {
							check = true;
						}
					}
				}
			}
		}
		return check;
	}

}