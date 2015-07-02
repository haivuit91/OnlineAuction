package com.asiantech.haivu.onlineauction.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiantech.haivu.onlineauction.service.ImageService;

@Controller
public class MainController {

	@Autowired
	private ImageService imageSv;

	// TODO default method is GET you can remove method attribute
	@RequestMapping(value = "/")
	public String goHomePage(ModelMap model) {
		return "redirect:/auctions/all";
	}

	@RequestMapping(value = "/auction")
	public String goAuctionPage(ModelMap model) {
		return "redirect:/auctions/all";
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
