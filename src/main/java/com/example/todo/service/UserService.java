package com.example.todo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.todo.model.CustomUserDetails;
import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findByEmailAndFetchAuthorities(String email) {
		return userRepository.findByEmailAndFetchAuthorities(email);
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
															user.getAuthorities());
		
		return principal;
	}
	
}
