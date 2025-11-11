package com.academy.ccrpms.application.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.job.repository.JobRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final SubmissionRepository submissionRepository;

    // 🟩 Nộp hồ sơ ứng tuyển
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

    // 🟩 Lấy danh sách hồ sơ của người dùng
    public List<Application> getApplicationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return applicationRepository.findByUser(user);
    }

    // 🟩 Theo dõi trạng thái hồ sơ
    public List<Map<String, Object>> getApplicationStatuses(Long userId) {
        List<Application> apps = applicationRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Application app : apps) {
            Map<String, Object> map = new HashMap<>();
            map.put("jobTitle", app.getJob() != null ? app.getJob().getTitle() : "Chưa xác định");
            map.put("location", app.getJob() != null ? app.getJob().getLocation() : "N/A");
            map.put("submittedAt", app.getCreatedAt());
            map.put("statusCode", app.getStatus() != null ? app.getStatus().name() : "UNKNOWN");

            String statusText;
            if (app.getStatus() == null) {
                statusText = "Không rõ trạng thái";
            } else {
                switch (app.getStatus()) {
                    case PENDING:
                        statusText = "Đang chờ duyệt";
                        break;
                    case INTERVIEW:
                        statusText = "Được phỏng vấn";
                        break;
                    case REJECTED:
                        statusText = "Trượt";
                        break;
                    case HIRED:
                        statusText = "Đỗ";
                        break;
                    default:
                        statusText = "Không rõ trạng thái";
                }
            }

            map.put("status", statusText);
            result.add(map);
        }
        return result;
    }

    // 🟩 Xem kết quả tuyển dụng (điểm thi + trạng thái)
    public List<Map<String, Object>> getApplicationResults(Long userId) {
        List<Application> apps = applicationRepository.findByUserId(userId);
        List<Submission> submissions = submissionRepository.findByUserId(userId);
        List<Map<String, Object>> results = new ArrayList<>();

        for (Application app : apps) {
            Map<String, Object> map = new HashMap<>();
            map.put("jobTitle", app.getJob() != null ? app.getJob().getTitle() : "N/A");
            map.put("location", app.getJob() != null ? app.getJob().getLocation() : "N/A");
            map.put("submittedAt", app.getCreatedAt());
            map.put("status", app.getStatus() != null ? app.getStatus().name() : "UNKNOWN");

            // tìm submission tương ứng
            Submission submission = submissions.stream()
                    .filter(s -> s.getApplication() != null && s.getApplication().getId().equals(app.getId()))
                    .findFirst()
                    .orElse(null);

            map.put("examScore", submission != null ? submission.getScore() : null);
            map.put("interviewScore", null); // sẽ thêm module phỏng vấn sau

            String finalResult;
            if (app.getStatus() == null) {
                finalResult = "Không rõ trạng thái";
            } else {
                switch (app.getStatus()) {
                    case HIRED:
                        finalResult = "Đỗ";
                        break;
                    case REJECTED:
                        finalResult = "Trượt";
                        break;
                    case INTERVIEW:
                        finalResult = "Đang phỏng vấn";
                        break;
                    default:
                        finalResult = "Đang chờ duyệt";
                }
            }
            map.put("finalResult", finalResult);

            results.add(map);
        }
        return results;
    }
}
