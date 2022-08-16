package com.example.todo.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "AUTHORITIES")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_ROLE = "USER";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	private String role;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
	private Set<User> users = new HashSet<>();

	@Override
	public String getAuthority() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return users;
	}
	
	


}
