package com.example.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.todo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM USERS u WHERE u.email= :email", nativeQuery = true)
	Optional<User> findByEmail(String email);

	@Query(value = "SELECT u FROM User u JOIN FETCH u.authorities WHERE u.email = :email")
	Optional<User> findByEmailAndFetchAuthorities(String email);
	
	// LEFT JOIN FETCH is necessary to handle the case where no activities are present
	// but we still want to get the user info. With only the JOIN FETCH, if the user
	// don't have any activities the query returns "null"
	@Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.activities WHERE u.email = :email")
	Optional<User> findByEmailAndFetchActivities(String email);

}
