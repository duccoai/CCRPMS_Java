package com.academy.ccrpms.config;

import com.academy.ccrpms.user.entity.Role;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.RoleRepository;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Admin
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123456")) // encode BCrypt
                    .fullName("Administrator")
                    .email("admin@example.com")
                    .active(true)
                    .role(adminRole)
                    .build();
            userRepository.save(admin);
        }

        // Recruiter (ví dụ)
        if (!userRepository.existsByUsername("recruiter1")) {
            Role recruiterRole = roleRepository.findByName("RECRUITER").orElseThrow();
            User recruiter = User.builder()
                    .username("recruiter1")
                    .password(passwordEncoder.encode("123456"))
                    .fullName("Recruiter One")
                    .email("recruiter1@example.com")
                    .active(true)
                    .role(recruiterRole)
                    .build();
            userRepository.save(recruiter);
        }

        // Candidate (ví dụ)
        if (!userRepository.existsByUsername("candidate1")) {
            Role candidateRole = roleRepository.findByName("CANDIDATE").orElseThrow();
            User candidate = User.builder()
                    .username("candidate1")
                    .password(passwordEncoder.encode("123456"))
                    .fullName("Candidate One")
                    .email("candidate1@example.com")
                    .active(true)
                    .role(candidateRole)
                    .build();
            userRepository.save(candidate);
        }
    }
}
