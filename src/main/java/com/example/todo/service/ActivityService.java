package com.example.todo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.todo.model.Activity;
import com.example.todo.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteById(UUID id) {
        activityRepository.deleteById(id);
    }

	public List<Activity> findByUserIdOrderByCreatedAtAsc(UUID userId) {
		return activityRepository.findByUserIdOrderByCreatedAtAsc(userId);
	}

}
