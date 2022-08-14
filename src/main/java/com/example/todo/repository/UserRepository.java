package com.example.todo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.todo.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	@Query(value = "SELECT u FROM User u JOIN FETCH u.authorities WHERE u.email = :email")
	Optional<User> findByEmailAndFetchAuthorities(String email);
	
	Optional<User> findByEmail(String email);

}
