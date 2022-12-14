package com.example.todo.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.mapper.UserMapper;
import com.example.todo.model.CustomUserDetails;
import com.example.todo.model.User;
import com.example.todo.model.UserCreationDTO;
import com.example.todo.model.VerificationToken;
import com.example.todo.repository.UserRepository;
import com.example.todo.repository.VerificationTokenRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserMapper userMapper;
	private final NotificationService notificationService;
	private final PasswordEncoder encoder;
		
	public UserService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, UserMapper userMapper, NotificationService notificationService, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.userMapper = userMapper;
		this.notificationService = notificationService;
		this.encoder = encoder;
	}
	
	public Optional<User> findByEmailAndFetchAuthorities(String email) {
		return userRepository.findByEmailAndFetchAuthorities(email);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User getReferenceById(UUID id) {
		return userRepository.getReferenceById(id);
	}
		
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optUser = findByEmailAndFetchAuthorities(email);
		
		if (optUser.isEmpty()) {
			throw new UsernameNotFoundException("User with email: " + email +" not found");
		}
		User user = optUser.get();
		CustomUserDetails principal = new CustomUserDetails(user.getEmail(),
															user.getPassword(),
															user.getId(),
															user.getAuthorities(),
															user.isActivated());
		
		return principal;
	}
	
	public User registerUser(UserCreationDTO userCreationDTO) {

		User user = userMapper.toUser(userCreationDTO);
		
		user = userRepository.save(user);

		// Async functionality
		notificationService.sendActivationEmail(user.getEmail());

		return user;
	}

	public void activateUser(String token) {

		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

		if (verificationToken.getExpiryDate().after(new Date())) {

			User user = userRepository.findByEmail(verificationToken.getEmail()).get();
			user.setActivated(true);

			userRepository.save(user);
			verificationTokenRepository.delete(verificationToken);

		}
	}
	
	public void resetPassword(String token, String newPassword) {
		
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

		if (verificationToken.getExpiryDate().after(new Date())) {

			User user = userRepository.findByEmail(verificationToken.getEmail()).get();
			
			user.setPassword(encoder.encode(newPassword));

			userRepository.save(user);
			verificationTokenRepository.delete(verificationToken);

		}
		
	}

}
