package com.example.todo.service;

import org.springframework.stereotype.Service;

import com.example.todo.model.Authority;
import com.example.todo.repository.AuthorityRepository;

@Service
public class AuthorityService {

	private final AuthorityRepository authorityRepository;
	
	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}
	
	public Authority findByRole(String role) {
		return authorityRepository.findByRole(role);
	}
	
}
