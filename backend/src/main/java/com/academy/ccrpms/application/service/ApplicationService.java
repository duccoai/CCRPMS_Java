package com.academy.ccrpms.application.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus; // ✅ thêm dòng này
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.job.repository.JobRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    // Nộp hồ sơ ứng tuyển
    public Application submitApplication(Long userId, Long jobId) {
        System.out.println(">>> SubmitApplication called for userId=" + userId + ", jobId=" + jobId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application app = Application.builder()
                .user(user)
                .job(job)
                .status(ApplicationStatus.PENDING)
                .build();

        return applicationRepository.save(app);
    }

    // Lấy danh sách hồ sơ của người dùng
    public List<Application> getApplicationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return applicationRepository.findByUser(user);
    }
}
