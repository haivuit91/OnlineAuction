package com.asiantech.haivu.onlineauction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.asiantech.haivu.onlineauction.service.VerificationMailService;

@Service(VerificationMailService.NAME)
public class VerificationMailServiceImpl implements VerificationMailService {

	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;

	@Override
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

}
