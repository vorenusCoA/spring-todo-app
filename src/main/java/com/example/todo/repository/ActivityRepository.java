package com.example.todo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.todo.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

	List<Activity> findByUserIdOrderByCreatedAtAsc(UUID userId);
	
	// Implementing deleteById query so that Hibernate doesn't make a SELECT before DELETE
	@Modifying
	@Query(value = "DELETE FROM ACTIVITIES WHERE id = :id", nativeQuery = true)
	void deleteById(UUID id);

}
