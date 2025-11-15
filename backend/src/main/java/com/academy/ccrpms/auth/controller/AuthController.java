package com.academy.ccrpms.auth.controller;

import com.academy.ccrpms.auth.model.LoginRequest;
import com.academy.ccrpms.auth.model.LoginResponse;
import com.academy.ccrpms.auth.service.AuthService;
import com.academy.ccrpms.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = authService.register(user);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            log.error("Register failed", ex);
            return ResponseEntity
                    .status(500)
                    .body(new ErrorResponse("REGISTER_FAILED", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse resp = authService.login(request);
            return ResponseEntity.ok(resp);
        } catch (RuntimeException ex) {
            log.error("Login failed for user {}", request.getUsername(), ex);
            return ResponseEntity.status(500)
                    .body(new AuthController.ErrorResponse("LOGIN_FAILED", ex.getMessage()));
        }
    }


    // Helper class để gửi lỗi chuẩn
    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}
