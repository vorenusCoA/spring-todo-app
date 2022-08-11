package com.example.todo.model;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private UUID userId;
	private Set<Authority> authorities;
	
	public CustomUserDetails(String username, String password, UUID userID, Set<Authority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.userId = userID;
		this.authorities = authorities;
	}

	public UUID getUserId() {
		return userId;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
