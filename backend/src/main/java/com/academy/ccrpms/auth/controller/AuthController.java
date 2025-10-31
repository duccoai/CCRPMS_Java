package com.academy.ccrpms.auth.controller;

import com.academy.ccrpms.auth.model.LoginRequest;
import com.academy.ccrpms.auth.model.LoginResponse;
import com.academy.ccrpms.auth.service.AuthService;
import com.academy.ccrpms.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
