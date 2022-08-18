package com.example.todo.model;

import javax.validation.constraints.NotBlank;

import com.example.todo.validator.ValidateFieldsMatch;

@ValidateFieldsMatch(
		field = "password",
		fieldMatch = "confirmPassword",
		message = "Passwords do not match")
public class PasswordResetDTO {

	@NotBlank
	private String password;
	private String confirmPassword;
	
	private String token;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
