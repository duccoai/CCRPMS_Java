package com.academy.ccrpms.auth.service;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.auth.model.LoginRequest;
import com.academy.ccrpms.auth.model.LoginResponse;
import com.academy.ccrpms.user.entity.Role;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.RoleRepository;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // 🔹 Đăng ký tài khoản mới
    public User register(User user) {
        // Gán role mặc định CANDIDATE
        Role defaultRole = roleRepository.findByName("CANDIDATE")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRole(defaultRole);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 🔹 Đăng nhập
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(token, user.getId(), user.getUsername(), user.getRole().getName());
    }
}
