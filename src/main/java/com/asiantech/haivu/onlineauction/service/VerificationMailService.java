package com.asiantech.haivu.onlineauction.service;

public interface VerificationMailService {

	public static final String NAME = "verificationMailService";

	void sendMail(String to, String subject, String body);

}
