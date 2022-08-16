package com.example.todo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

	VerificationToken findByToken(String token);

}
