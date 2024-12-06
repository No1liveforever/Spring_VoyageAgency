package com.VoyageConnect.AgenceDeVoyage.controller;

import com.VoyageConnect.AgenceDeVoyage.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

   
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.registerUser(username, password, role);
        return "User registered successfully!";
    }

    // Login will be handled by Spring Security (no need to explicitly define it here)
}
