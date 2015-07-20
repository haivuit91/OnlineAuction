package com.asiantech.haivu.onlineauction.controller;

import java.util.Iterator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/user/")
public class UserController extends ShowPage {
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "setting")
	public String goSettingPage(ModelMap model) {
		model = showUserPage("Account Setting", "user/setting", model);
		return Constants.TEMPLATE_HOME;
	}
	
	@RequestMapping(value = "change-information", method = RequestMethod.POST)
	public String saveUser(@Valid Account accountInfo, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("layout", "user/setting");
			return Constants.TEMPLATE_HOME;
		}
		// Account accountEntity = mapper.map(account, Account.class);
		accountService.changeInfoAccount(accountInfo);
		return "redirect:/user/setting";
	}
	
	@RequestMapping(value = "change-password", method = RequestMethod.POST)
	public String goChangePwdPage(@RequestParam("currentPwd") String currentPwd, @RequestParam("newPwd") String newPwd, 
			@RequestParam("rePwd") String rePwd, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String msg = "";
		if(!newPwd.equals(rePwd)) {
			model.put("errorRePwd", true);
			msg = "Your re enter password was incorrect.";
		} else if(!accountService.changePassword(currentPwd, newPwd, auth.getName())) {
			model.put("errorCurrentPwd", true);
			msg = "Your password was incorrect.";
		} else {
			model.put("success", true);
			msg = "Change password successfully.";
		}
		model.put("changePwd", true);
		model.put("message", msg);
		model = showUserPage("Account Setting", "user/setting", model);
		return Constants.TEMPLATE_HOME;
	}
	
	@RequestMapping(value = "change-avatar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public boolean changeAvatar(MultipartHttpServletRequest request) {
		boolean check = false;
		Iterator<String> itrator = request.getFileNames();
        MultipartFile multiFile = request.getFile(itrator.next());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(accountService.changeAvatar(multiFile, auth.getName())) {
        	check = true;
        }
        return check;
	}

}
