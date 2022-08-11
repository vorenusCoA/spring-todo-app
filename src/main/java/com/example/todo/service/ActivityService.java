package com.example.todo.service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public void delete(UUID id) {
        activityRepository.deleteById(id);
    }

	public List<Activity> findByUserId(UUID userId) {
		return activityRepository.findByUserId(userId);
	}
	
	public List<Activity> sortAndSetOrder(List<Activity> activities) {
		
		List<Activity> sortedActivities = activities.stream()
				   			   						.sorted(Comparator.comparing(Activity::getCreatedAt))
				   			   						.collect(Collectors.toList());

		for (int i = 0; i < sortedActivities.size(); i++) {
			sortedActivities.get(i).setOrder(i + 1);
		}
		
		return sortedActivities;
	}

}
