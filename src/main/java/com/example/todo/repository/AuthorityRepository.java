package com.example.todo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

	Authority findByRole(String role);
	
}
