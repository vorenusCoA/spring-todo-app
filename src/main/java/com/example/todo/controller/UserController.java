package com.example.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.model.UserCreationDTO;
import com.example.todo.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		UserCreationDTO userCreationDTO = new UserCreationDTO();
		model.addAttribute("userCreationDTO", userCreationDTO);
		
		return "registration";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute UserCreationDTO userCreationDTO, BindingResult result) {
		
		if (result.hasErrors()) {
			return "registration";
		}
		
		userService.registerUser(userCreationDTO);
		
		return "redirect:/register?success=true";
	}
	
	@GetMapping("/activate")
	public String activateUser(@RequestParam("token") String token) {
		
		userService.activateUser(token);
		
		return "activation";
	}
	
}
