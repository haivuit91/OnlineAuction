package com.asiantech.haivu.onlineauction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.asiantech.haivu.onlineauction.service.VerificationMailService;

@Service(VerificationMailService.NAME)
public class VerificationMailServiceImpl implements VerificationMailService {

	@Autowired
	private MailSender mailSender;
	
	private String to = "";
    private String subject = "";
    private String body = "";

	@Override
    public void verifyEmail(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

	public void sendMail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

	@Override
	public void run() {
		this.sendMail();
	}

}
