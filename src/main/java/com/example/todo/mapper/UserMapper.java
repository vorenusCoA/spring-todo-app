package com.example.todo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.todo.model.Authority;
import com.example.todo.model.User;
import com.example.todo.model.UserCreationDTO;
import com.example.todo.service.AuthorityService;

@Component
public class UserMapper {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	public User toUser(UserCreationDTO userCreationDTO) {
				
		User user = new User();
		user.setFirstName(userCreationDTO.getFirstName());
		user.setLastName(userCreationDTO.getLastName());
		user.setEmail(userCreationDTO.getEmail());
		user.setPassword(encoder.encode(userCreationDTO.getPassword()));
		user.setActivated(false);
		
		Authority authority = authorityService.findByRole(Authority.DEFAULT_ROLE);		
		user.addAuthority(authority);
		
		return user;
	}

}
