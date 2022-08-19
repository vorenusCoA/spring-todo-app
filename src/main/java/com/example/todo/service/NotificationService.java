package com.example.todo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.todo.model.User;
import com.example.todo.model.VerificationToken;
import com.example.todo.repository.UserRepository;
import com.example.todo.repository.VerificationTokenRepository;

@Service
public class NotificationService {
	
	private static final String SERVER_URL = "http://localhost:8080/";
	
	private final VerificationTokenRepository verificationTokenRepository;
	private final JavaMailSender mailSender;
	private final UserRepository userRepository;
	
	public NotificationService(VerificationTokenRepository verificationTokenRepository, JavaMailSender mailSender, UserRepository userRepository) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.mailSender = mailSender;
		this.userRepository = userRepository;
	}

	@Async
	public void sendActivationEmail(String emailAddress) {

		String token = UUID.randomUUID().toString();
		createVerificationToken(emailAddress, token, VerificationToken.EXPIRATION_FOR_ACCOUNT_ACTIVATION);

		SimpleMailMessage mail = createMail(emailAddress,
											"Activate your TODO-APP user",
											"Please, clik to activate your account: \r\n" + SERVER_URL + "activate?token=" + token);

		mailSender.send(mail);
	}
	
	@Async
	public void sendPasswordResetEmail(String emailAddress) {
		
		// If the email is not in the DB, do nothing
		Optional<User> user = userRepository.findByEmail(emailAddress);
		if (user.isEmpty()) {
			return;
		}

		String token = UUID.randomUUID().toString();
		createVerificationToken(emailAddress, token, VerificationToken.EXPIRATION_FOR_PASSWORD_RESET);

		SimpleMailMessage mail = createMail(emailAddress,
											"TODO-APP: reset your password",
											"Please, clik to reset your password: \r\n" + SERVER_URL + "password-reset?token=" + token);
		
		mailSender.send(mail);
	}

	private void createVerificationToken(String email, String token, int expirationInMins) {

		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setEmail(email);
		verificationToken.setToken(token);
		verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(expirationInMins));

		verificationTokenRepository.save(verificationToken);
	}
	
	private SimpleMailMessage createMail(String to, String subject, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setFrom("no.reply@todo-app.com");
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(message);
		
		return mail;
	}
	
}
