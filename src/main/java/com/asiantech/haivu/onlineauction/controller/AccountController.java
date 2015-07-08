package com.asiantech.haivu.onlineauction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/account/")
public class AccountController extends ShowPage {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CategoryService categorySv;

	@Autowired
	private CategorySubService categorySubSv;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String goLoginPage(Account account, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/auctions/list";
		}
		model = showHomePage("Login", "login", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String goSignupPage(Account account, ModelMap model) {
		model = showHomePage("Register", "register", model);
		return Constants.TEMPLATE_HOME;
	}

	// extract to method with block if - else
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveAccount(
			@Valid Account account,
			@RequestParam(value = "confirmPassword", required = true) String confirmPassword,
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model = showHomePage("Register", "register", model);
			return Constants.TEMPLATE_HOME;
		}
		if (accountService.findAccountByAccountName(account.getAccountName()) != null) {
			model.put("registerError", true);
			model.put("registerErrorAccName", true);
			model.put("registerErrorMsg",
					"Account name is invalid or already taken.");
		} else if (accountService.findAccountByEmail(account.getEmail()) != null) {
			model.put("registerError", true);
			model.put("registerErrorEmail", true);
			model.put("registerErrorMsg", "Email is invalid or already taken.");
		} else if (!account.getPwd().contains(confirmPassword)) {
			model.put("registerError", true);
			model.put("registerErrorPwd", true);
			model.put("registerErrorMsg",
					"The password and its confirm are not the same");
		} else {
			accountService.userRegister(account);
			return "redirect:/account/successful";
		}
		model = showHomePage("Register", "register", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "successful", method = RequestMethod.GET)
	public String registerSuccess(ModelMap model) {
		model = showHomePage("Register", "successful", model);
		return Constants.TEMPLATE_HOME;
	}
	
	@RequestMapping(value = "verify-email")
	public String verifyEmail(
			@RequestParam(value = "uid", required = true) long uid,
			@RequestParam(value = "code", required = true) String code, ModelMap model) {
		if(accountService.updateStatusByIdAndVerification(uid, code)){
			model = showHomePage("Account verified", "verify_email", model);
		} else {
			model = showHomePage("Account verified", "verify_email", model);
		}
		return Constants.TEMPLATE_HOME;
	}

}
