package com.example.todo.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.example.todo.model.Activity;
import com.example.todo.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserService userService;

    public ActivityService(ActivityRepository activityRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
    }

    public Activity save(@NotNull Activity activity, @NotNull UUID userId) {
    	
    	activity.setUser(userService.getReferenceById(userId));
    	activity.setCreatedAt(Timestamp.from(Instant.now()));
    	
        return activityRepository.save(activity);
    }

    public void deleteById(UUID id) {
        activityRepository.deleteById(id);
    }

	public List<Activity> findByUserIdOrderByCreatedAtAsc(UUID userId) {
		return activityRepository.findByUserIdOrderByCreatedAtAsc(userId);
	}

}
