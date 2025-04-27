package com.ali.movieapp.controller;

import com.ali.movieapp.model.User;
import com.ali.movieapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("user") User user, 
                                BindingResult result) {
        // Custom validation for password confirmation
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.addError(new FieldError("user", "confirmPassword", 
                                          "Passwords do not match"));
        }
        
        // Check if username already exists
        if (userService.usernameExists(user.getUsername())) {
            result.addError(new FieldError("user", "username", 
                                          "Username already exists"));
        }
        
        if (result.hasErrors()) {
            return "signup";
        }
        
        userService.saveUser(user);
        return "redirect:/login?registered";
    }
}