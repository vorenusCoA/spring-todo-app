package com.example.todo.service;

import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.todo.model.User;
import com.example.todo.model.VerificationToken;
import com.example.todo.repository.VerificationTokenRepository;

@Service
public class NotificationService {
	
	private static final String SERVER_URL = "http://localhost:8080/";
	
	private final VerificationTokenRepository verificationTokenRepository;
	private final JavaMailSender mailSender;
	
	public NotificationService(VerificationTokenRepository verificationTokenRepository, JavaMailSender mailSender) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.mailSender = mailSender;
	}

	@Async
	public void sendActivationEmail(User user) {

		String token = UUID.randomUUID().toString();
		createVerificationToken(user, token);

		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("no.reply@todo-app.com");
		email.setTo(user.getEmail());
		email.setSubject("Activate your TODO-APP user");
		email.setText("Please, clik to activate your account: \r\n" + SERVER_URL + "activate?token=" + token);

		mailSender.send(email);
	}

	private void createVerificationToken(User user, String token) {

		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setEmail(user.getEmail());
		verificationToken.setToken(token);
		verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));

		verificationTokenRepository.save(verificationToken);
	}
	
}
