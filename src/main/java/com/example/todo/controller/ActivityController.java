package com.example.todo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.todo.model.Activity;
import com.example.todo.model.CustomUserDetails;
import com.example.todo.service.ActivityService;


@Controller
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public String findAll(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
    	
    	// Initializing the 'activity' attribute for the 'activity-form'
    	// because the form is in a modal dialog on the same html file
    	Activity newActivity = new Activity();
    	model.addAttribute("activity", newActivity);
    	    	
    	List<Activity> sortedActivities = activityService.findByUserIdOrderByCreatedAtAsc(currentUser.getUserId());
        model.addAttribute("activities", sortedActivities);
        
        return "activities";
    }

    @PostMapping("/activities")
    public String save(@AuthenticationPrincipal CustomUserDetails currentUser, @ModelAttribute("activity") Activity activity, BindingResult result) {
    	
    	// Since the form for a new activity is in a modal dialog the client will validate it via JS
    	// If at this point there is still an error we won't save and just make a refresh
    	if (!result.hasErrors()) {
    		activityService.save(activity, currentUser.getUserId());
    	}
    	
        return "redirect:/activities";
    }

    @DeleteMapping("/activities/{id}")
    public String delete(@PathVariable("id") UUID id) {    	
        activityService.deleteById(id);
        return "redirect:/activities";
    }

}
