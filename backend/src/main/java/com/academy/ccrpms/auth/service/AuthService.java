package com.academy.ccrpms.auth.service;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.auth.model.LoginRequest;
import com.academy.ccrpms.auth.model.LoginResponse;
import com.academy.ccrpms.auth.model.UserResponse;
import com.academy.ccrpms.user.entity.Role;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.RoleRepository;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    // Đăng ký
    public User register(User user) {
        Role defaultRole = roleRepository.findByName("CANDIDATE")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRole(defaultRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Đăng nhập (robust)
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            String token = jwtService.generateToken(userDetails);

            UserResponse.RoleResponse roleResponse = new UserResponse.RoleResponse(
                    user.getRole().getId(),
                    user.getRole().getName()
            );

            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    roleResponse
            );

            return new LoginResponse(token, userResponse);
        } catch (BadCredentialsException bce) {
            log.warn("Bad credentials for user {}", request.getUsername());
            throw new RuntimeException("Bad credentials");
        } catch (Exception ex) {
            log.error("Error during login for user " + request.getUsername(), ex);
            throw new RuntimeException("Login failed: " + ex.getMessage());
        }
    }
}
