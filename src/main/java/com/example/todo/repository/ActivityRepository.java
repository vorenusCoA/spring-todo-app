package com.example.todo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

	List<Activity> findByUserIdOrderByCreatedAtAsc(UUID userId);

}
