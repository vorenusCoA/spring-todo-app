package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
