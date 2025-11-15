package com.academy.ccrpms.admin.service;

import com.academy.ccrpms.admin.dto.AdminStatsDTO;
import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.entity.Exam;
import com.academy.ccrpms.exam.repository.ExamRepository;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.repository.InterviewRepository;
import com.academy.ccrpms.user.entity.Role;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.RoleRepository;
import com.academy.ccrpms.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final ExamRepository examRepository;
    private final PasswordEncoder passwordEncoder;

    // Tạo user với role
    public User createUserWithRole(User user, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createRecruiter(User user) {
        return createUserWithRole(user, "RECRUITER");
    }

    public User createAdmin(User user) {
        return createUserWithRole(user, "ADMIN");
    }

    public User updateUser(Long id, User data) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (data.getFullName() != null) u.setFullName(data.getFullName());
        if (data.getEmail() != null) u.setEmail(data.getEmail());
        if (data.getPassword() != null && !data.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(data.getPassword()));
        }
        return userRepository.save(u);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application updateFinalDecision(Long id, String result) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if ("PASS".equalsIgnoreCase(result) || "APPROVE".equalsIgnoreCase(result) || "APPROVED".equalsIgnoreCase(result)) {
            app.setStatus(ApplicationStatus.APPROVED);
        } else {
            app.setStatus(ApplicationStatus.REJECTED);
        }
        return applicationRepository.save(app);
    }

    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    public Exam toggleExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setActive(!exam.isActive());
        return examRepository.save(exam);
    }

    public AdminStatsDTO getStatistics() {
        // Tổng số users có role CANDIDATE
        long totalCandidates = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() != null)          // tránh null role
                .filter(u -> "CANDIDATE".equalsIgnoreCase(u.getRole().getName()))
                .count();

        long totalApplications = applicationRepository.count();

        // Đảm bảo countByStatus tồn tại trong ApplicationRepository
        long passed = applicationRepository.countByStatus(ApplicationStatus.APPROVED);
        long failed = applicationRepository.countByStatus(ApplicationStatus.REJECTED);

        return new AdminStatsDTO(totalCandidates, totalApplications, passed, failed);
    }

}
