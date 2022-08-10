package com.example.todo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.todo.model.User;
import com.example.todo.repository.ActivityRepository;
import com.example.todo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository, ActivityRepository activityRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Optional<User> findByEmailAndFetchAuthorities(String email) {
		return userRepository.findByEmailAndFetchAuthorities(email);
	}
	
	public Optional<User> findByEmailAndFetchActivities(String email) {
		return userRepository.findByEmailAndFetchActivities(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optUser = findByEmailAndFetchAuthorities(email);
		
		if (optUser.isEmpty()) {
			throw new UsernameNotFoundException("User with email: " + email +" not found");
		}
		
		return optUser.get();
	}
	
}
