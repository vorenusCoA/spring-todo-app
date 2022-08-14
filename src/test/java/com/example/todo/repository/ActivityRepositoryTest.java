package com.example.todo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.model.Activity;
import com.example.todo.model.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActivityRepositoryTest {

	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User testUser;
	private UUID activityId;
	
	private static final String NEW_ACTIVITY_DESCRIPTION = "testing activity";
	
	@Test
	@Order(1)
	void loadTestUser() {
		Optional<User> optTestUser = userRepository.findByEmail("user@test.com");
		
		assertThat(optTestUser).isNotEmpty();
		
		testUser = optTestUser.get();
	}

	@Test
	@Order(2)
	void addNewTestActivity() {
		
		Activity activity = new Activity();
		activity.setUser(testUser);
		activity.setDescription(NEW_ACTIVITY_DESCRIPTION);
		activity.setCreatedAt(Timestamp.from(Instant.now()));
		
		activity = activityRepository.save(activity);
		
		assertThat(activity.getId()).isNotNull();
		
		activityId = activity.getId();
	}
	
	@Test
	@Order(3)
	void deleteTestActivityById() {
		
		activityRepository.deleteById(activityId);
		
		Optional<Activity> activity = activityRepository.findById(activityId);
		
		assertThat(activity).isEmpty();
	}
	
}
