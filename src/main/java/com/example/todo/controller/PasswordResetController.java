package com.example.todo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.model.PasswordResetDTO;
import com.example.todo.service.NotificationService;
import com.example.todo.service.UserService;

@Controller
public class PasswordResetController {

	private final NotificationService notificationService;
	private final UserService userService;
	
	public PasswordResetController(NotificationService notificationService, UserService userService) {
		this.notificationService = notificationService;
		this.userService = userService;
	}
	
	@GetMapping("/password")
	public String showForgotPasswordForm() {
		return "forgotPassword";
	}
	
	@PostMapping("/password")
	public String initiatePasswordReset(@RequestParam("email") String email) {
		
		// TODO validations
		
		notificationService.sendPasswordResetEmail(email);
		
		return "redirect:/password?success=true";
	}
	
	@GetMapping("/password-reset")
	public String showPasswordResetForm(@RequestParam("token") String token, Model model) {
		
		PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
		passwordResetDTO.setToken(token);
		model.addAttribute("passwordResetDTO", passwordResetDTO);
		
		return "passwordReset";
	}
	
	@PostMapping("/password-reset")
	public String resetPassword(@Valid @ModelAttribute PasswordResetDTO passwordResetDTO, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/password-reset?token=" + passwordResetDTO.getToken();
		}
		
		userService.resetPassword(passwordResetDTO.getToken(), passwordResetDTO.getPassword());
		
		return "redirect:/password-reset?success=true&token=0";
	}
	
}
