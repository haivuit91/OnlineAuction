package com.asiantech.haivu.onlineauction.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface ImageService {

	public static final String NAME = "imageService";

	ResponseEntity<byte[]> getResponseImage(String urlImage) throws IOException;

}
