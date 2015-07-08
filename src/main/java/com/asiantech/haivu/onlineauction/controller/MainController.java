package com.asiantech.haivu.onlineauction.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiantech.haivu.onlineauction.service.ImageService;

@Controller
public class MainController {

	@Autowired
	private ImageService imageSv;

	@RequestMapping(value = "/")
	public String goHomePage(ModelMap model) {
		return "redirect:/auctions/all";
	}

	@RequestMapping(value = "/auction")
	public String goAuctionPage(ModelMap model) {
		return "redirect:/auctions/all";
	}

	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkLogin() {
		boolean check = false;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			check = true;
		}
		return check;
	}

	@RequestMapping("/image/{pathImage:.*}")
	public ResponseEntity<byte[]> testphoto(@PathVariable String pathImage,
			HttpServletResponse response) throws IOException {

		if (StringUtils.isBlank(pathImage)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		ResponseEntity<byte[]> responseEntity = imageSv
				.getResponseImage(pathImage);
		if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return responseEntity;
	}

}
