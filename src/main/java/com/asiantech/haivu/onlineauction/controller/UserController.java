package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private CategoryService categorySv;

	@Autowired
	private CategorySubService categorySubSv;

	@RequestMapping(value = "{accountName}", method = RequestMethod.GET)
	public String goProfilePage(@PathVariable String accountName,
			HttpSession session, ModelMap model) {
		String checkAccount = session.getAttribute("sessionAccName").toString();
		// should use StringUtil to check equal two string
		if (!checkAccount.contentEquals(accountName)) {
			return "redirect:/auctions/list";
		}
		return showHomePage("My Account", "user/profile", model);
	}

	public String showHomePage(String breadcrumbs, String layout, ModelMap model) {
		// Get list Category
		List<Category> listCategory = categorySv.findAllCategory();
		// Get list CategorySub
		List<CategorySub> listCategorySub = categorySubSv.findAllCategorySub();
		// Set object view
		model.put("listCate", listCategory);
		model.put("listCateSub", listCategorySub);
		model.put("layout", layout);
		model.put("breadcrumbs", breadcrumbs);
		return Constants.TEMPLATE_HOME;
	}

}
