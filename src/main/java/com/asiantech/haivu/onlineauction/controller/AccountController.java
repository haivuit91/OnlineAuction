package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.VerificationMailService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/account/")
public class AccountController {
	// KHong can su dung qualifier
	@Autowired
	private AccountService accountSv;

	@Autowired
	private CategoryService categorySv;

	@Autowired
	private CategorySubService categorySubSv;

	@Autowired
	private VerificationMailService verificationMailSv;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String goLoginPage(Account account, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/auctions/list";
		}
		return showHomePage("Login", "login", model);
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String goSignupPage(Account account, ModelMap model) {
		return showHomePage("Register", "register", model);
	}

	// extract to method with block if - else
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveAccount(
			@Valid Account account,
			@RequestParam(value = "confirmPassword", required = true) String confirmPassword,
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return showHomePage("Register", "register", model);
		} else {
			if (accountSv.findAccountByAccountName(account.getAccountName()) != null) {
				model.put("registerError", true);
				model.put("registerErrorAccName", true);
				model.put("registerErrorMsg",
						"Account name is invalid or already taken.");
				return showHomePage("Register", "register", model);
			} else if (accountSv.findAccountByEmail(account.getEmail()) != null) {
				model.put("registerError", true);
				model.put("registerErrorEmail", true);
				model.put("registerErrorMsg",
						"Email is invalid or already taken.");
				return showHomePage("Register", "register", model);
			} else if (!account.getPwd().contains(confirmPassword)) {
				model.put("registerError", true);
				model.put("registerErrorPwd", true);
				model.put("registerErrorMsg",
						"The password and its confirm are not the same");
				return showHomePage("Register", "register", model);
			} else {
				String pwd = passwordEncoder.encode(account.getPwd());
				Account acc = new Account(account.getAccountName(), pwd,
						account.getFullName(), account.getEmail(), "0");
				accountSv.saveAccount(acc);
				return "redirect:/account/successful";
			}
		}
	}

	@RequestMapping(value = "successful", method = RequestMethod.GET)
	public String registerSuccess(ModelMap model) {
		return showHomePage("Register", "successful", model);
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
