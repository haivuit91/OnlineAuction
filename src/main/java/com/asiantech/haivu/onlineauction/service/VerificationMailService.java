package com.asiantech.haivu.onlineauction.service;

public interface VerificationMailService extends Runnable {

	public static final String NAME = "verificationMailService";

	void verifyEmail(String to, String subject, String body);

}
