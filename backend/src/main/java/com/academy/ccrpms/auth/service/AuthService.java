package com.academy.ccrpms.auth.service;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.auth.model.LoginRequest;
import com.academy.ccrpms.auth.model.LoginResponse;
import com.academy.ccrpms.user.entity.User;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // 🔹 REGISTER
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 🔹 LOGIN
    public LoginResponse login(LoginRequest request) {
        // B1. Xác thực username và password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // B2. Lấy thông tin người dùng đã xác thực
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        // B3. Sinh JWT token
        String token = jwtService.generateToken(userDetails);

        // B4. Trả về response
        return new LoginResponse(token, user.getUsername());
    }
}
