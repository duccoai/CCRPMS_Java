package com.academy.ccrpms.application.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.job.repository.JobRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.repository.InterviewRepository;
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
    private final InterviewRepository interviewRepository;

    /**
     * Nộp hồ sơ ứng tuyển cho một job.
     *
     * @param candidateId ID của candidate
     * @param jobId       ID của job
     * @return Application vừa tạo
     */
    public Application submitApplication(Long candidateId, Long jobId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + candidateId));

        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

        Application app = new Application();
        app.setCandidate(candidate);
        app.setJob(job);
        app.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(app);
    }

    /**
     * Lấy danh sách hồ sơ của ứng viên
     *
     * @param candidateId ID của candidate
     * @return danh sách Application
     */
    public List<Application> getApplicationsByCandidate(Long candidateId) {
        return applicationRepository.findByCandidate_Id(candidateId);
    }

    /**
     * Theo dõi trạng thái hồ sơ ứng viên
     *
     * @param candidateId ID của candidate
     * @return danh sách map thông tin trạng thái hồ sơ
     */
    public List<Map<String, Object>> getApplicationStatuses(Long candidateId) {
        List<Application> apps = getApplicationsByCandidate(candidateId);
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
                    case PENDING -> statusText = "Đang chờ duyệt";
                    case INTERVIEW -> statusText = "Được phỏng vấn";
                    case REJECTED -> statusText = "Trượt";
                    case HIRED -> statusText = "Đỗ";
                    case APPROVED -> statusText = "Được duyệt";
                    default -> statusText = "Không rõ trạng thái";
                }
            }
            map.put("status", statusText);
            result.add(map);
        }

        return result;
    }

    /**
     * Xem kết quả tuyển dụng (kết hợp điểm thi + trạng thái)
     *
     * @param candidateId ID của candidate
     * @return danh sách map thông tin kết quả tuyển dụng
     */
    public List<Map<String, Object>> getApplicationResults(Long candidateId) {
        List<Application> apps = getApplicationsByCandidate(candidateId);
        List<Submission> submissions = submissionRepository.findByUser_Id(candidateId); // submission vẫn lưu User
        List<Map<String, Object>> results = new ArrayList<>();

        for (Application app : apps) {
            Map<String, Object> map = new HashMap<>();
            map.put("jobTitle", app.getJob() != null ? app.getJob().getTitle() : "N/A");
            map.put("location", app.getJob() != null ? app.getJob().getLocation() : "N/A");
            map.put("submittedAt", app.getCreatedAt());
            map.put("status", app.getStatus() != null ? app.getStatus().name() : "UNKNOWN");

            // Submission mới nhất
            Submission submission = submissions.stream()
                    .filter(s -> s.getApplication() != null && s.getApplication().getId().equals(app.getId()))
                    .findFirst()
                    .orElse(null);
            map.put("examScore", submission != null ? submission.getScore() : null);

            // Interview mới nhất
            Optional<Interview> interview = interviewRepository.findAll().stream()
                    .filter(iv -> iv.getApplication() != null && iv.getApplication().getId().equals(app.getId()))
                    .findFirst();
            map.put("interviewScore", interview.map(Interview::getScore).orElse(null));

            // Kết quả cuối cùng
            String finalResult;
            if (app.getStatus() == null) {
                finalResult = "Không rõ trạng thái";
            } else {
                switch (app.getStatus()) {
                    case HIRED -> finalResult = "Đỗ";
                    case REJECTED -> finalResult = "Trượt";
                    case INTERVIEW -> finalResult = "Đang phỏng vấn";
                    case APPROVED -> finalResult = "Được duyệt";
                    default -> finalResult = "Đang chờ duyệt";
                }
            }
            map.put("finalResult", finalResult);

            results.add(map);
        }

        return results;
    }
}
