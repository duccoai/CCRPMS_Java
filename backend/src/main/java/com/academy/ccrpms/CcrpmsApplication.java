package com.academy.ccrpms;

import com.academy.ccrpms.user.entity.Role;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.RoleRepository;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class CcrpmsApplication {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CcrpmsApplication.class, args);
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {

            // ----------------- ROLES -----------------
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

            Role recruiterRole = roleRepository.findByName("RECRUITER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "RECRUITER")));

            Role candidateRole = roleRepository.findByName("CANDIDATE")
                    .orElseGet(() -> roleRepository.save(new Role(null, "CANDIDATE")));

            // ----------------- USERS -----------------
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Administrator")
                        .email("admin@example.com")
                        .active(true)
                        .role(adminRole)
                        .build();
                userRepository.save(admin);
                System.out.println("Admin user created: admin / 123456");
            }

            if (userRepository.findByUsername("recruiter1").isEmpty()) {
                User recruiter = User.builder()
                        .username("recruiter1")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Recruiter One")
                        .email("recruiter1@example.com")
                        .active(true)
                        .role(recruiterRole)
                        .build();
                userRepository.save(recruiter);
                System.out.println("Recruiter user created: recruiter1 / 123456");
            }

            if (userRepository.findByUsername("candidate1").isEmpty()) {
                User candidate = User.builder()
                        .username("candidate1")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Candidate One")
                        .email("candidate1@example.com")
                        .active(true)
                        .role(candidateRole)
                        .build();
                userRepository.save(candidate);
                System.out.println("Candidate user created: candidate1 / 123456");
            }

        };
    }
}
