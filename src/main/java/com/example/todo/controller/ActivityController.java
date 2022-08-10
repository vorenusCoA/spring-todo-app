package com.example.todo.controller;

import java.util.Optional;

import javax.validation.Valid;

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

    @ModelAttribute
    public void addAttributes(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("activity", new Activity(currentUser));
    }

    @GetMapping("/activities/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Optional<Activity> activity = activityService.findById(id);
        if (activity.isPresent()) {
            model.addAttribute("activity", activity.get());
        } else {
            System.out.println("ACTIVITY NOT FOUND");
        }
        return "redirect:/activities";
    }

    @GetMapping("/activities")
    public String findAll(@AuthenticationPrincipal User currentUser, Model model) {
    	User user = userService.findByEmailAndFetchActivities(currentUser.getUsername()).get();
        model.addAttribute("activities", user.getActivities());
        return "activities";
    }

    @PostMapping("/activities")
    public String save(@Valid @ModelAttribute("activity") Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERRORS FOUND ON THE ACTIVITY!!!");
            return "redirect:/activities";
        }
        activityService.save(activity);
        return "redirect:/activities";
    }

    @DeleteMapping("/activities/{id}")
    public String delete(@PathVariable("id") Long id) {
        activityService.delete(id);
        return "redirect:/activities";
    }

}
