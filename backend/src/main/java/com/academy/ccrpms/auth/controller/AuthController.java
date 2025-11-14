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
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse resp = authService.login(request);
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            // log stacktrace (GlobalExceptionHandler also catches, but log earlier helps)
            log.error("Login failed", ex);
            // Return a clear message and 401 for authentication problems, otherwise 500
            String msg = ex.getMessage() != null ? ex.getMessage() : "Login failed";
            return ResponseEntity.status(500).body(new ErrorResponse("LOGIN_FAILED", msg));
        }
    }

    // small helper class used in response
    public static class ErrorResponse {
        public String code;
        public String message;
        public ErrorResponse(String c, String m) { this.code = c; this.message = m; }
    }
}
