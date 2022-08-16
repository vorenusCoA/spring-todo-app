package com.example.todo.model;

import javax.validation.constraints.NotBlank;

import com.example.todo.validator.ValidateFieldsMatch;

@ValidateFieldsMatch.List({
	@ValidateFieldsMatch(
			field = "email",
			fieldMatch = "confirmEmail",
			message = "Email addresses do not match"),
	@ValidateFieldsMatch(
			field = "password",
			fieldMatch = "confirmPassword",
			message = "Passwords do not match")
})
public class UserCreationDTO {

	@NotBlank
	private String firstName;
	
	@NotBlank	
	private String lastName;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String confirmEmail;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String confirmPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

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

}
