package com.example.todo.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.model.Activity;
import com.example.todo.model.CustomUserDetails;
import com.example.todo.model.User;
import com.example.todo.service.ActivityService;
import com.example.todo.service.UserService;


@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    public ActivityController(ActivityService activityService, UserService userService) {
        this.activityService = activityService;
        this.userService = userService;
    }

    @GetMapping("/activities")
    public String findAll(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
    	    	
    	List<Activity> sortedActivities = activityService.findByUserIdOrderByCreatedAtAsc(currentUser.getUserId());
    	    	    	
        model.addAttribute("activities", sortedActivities);
        return "activities";
    }

    @PostMapping("/activities")
    public String save(@RequestParam String description, @AuthenticationPrincipal CustomUserDetails currentUser, Activity activity) {
    	
    	User user = userService.getReferenceById(currentUser.getUserId());
    	
    	activity.setUser(user);
    	activity.setDescription(description);
        activity.setCreatedAt(Timestamp.from(Instant.now()));
        
        activityService.save(activity);
        
        return "redirect:/activities";
    }

    @DeleteMapping("/activities/{id}")
    public String delete(@PathVariable("id") UUID id) {    	
        activityService.deleteById(id);
        return "redirect:/activities";
    }

}
