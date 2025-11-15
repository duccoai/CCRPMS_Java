package com.academy.ccrpms.recruiter.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.recruiter.dto.ApplicationSummaryDTO;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final SubmissionRepository submissionRepository;

    /**
     * Lấy tất cả hồ sơ ứng tuyển của recruiter
     */
    public List<ApplicationSummaryDTO> getAllApplications(Long recruiterId) {
        List<Application> apps = applicationRepository.findByJob_Recruiter_Id(recruiterId);

        return apps.stream().map(app -> {
            Long applicationId = app.getId();
            String candidateUsername = app.getCandidate() != null ? app.getCandidate().getUsername() : "N/A";
            String candidateFullName = app.getCandidate() != null ? app.getCandidate().getFullName() : "N/A";
            String candidateEmail = app.getCandidate() != null ? app.getCandidate().getEmail() : "N/A";
            String jobTitle = app.getJob() != null ? app.getJob().getTitle() : "N/A";
            String status = app.getStatus() != null ? app.getStatus().name() : "PENDING";

            Optional<Interview> interviewOpt =
                    interviewRepository.findFirstByApplicationOrderByInterviewDateDesc(app);
            Long interviewId = interviewOpt.map(Interview::getId).orElse(null);
            Double interviewScore = interviewOpt.map(Interview::getScore).orElse(null);

            Optional<Submission> submissionOpt =
                    submissionRepository.findFirstByApplication_IdOrderByCreatedAtDesc(applicationId);
            Long submissionId = submissionOpt.map(Submission::getId).orElse(null);
            Double examScore = submissionOpt.map(Submission::getScore).orElse(null);

            return ApplicationSummaryDTO.builder()
                    .applicationId(applicationId)
                    .candidateUsername(candidateUsername)
                    .candidateFullName(candidateFullName)
                    .candidateEmail(candidateEmail)
                    .jobTitle(jobTitle)
                    .status(status)
                    .interviewId(interviewId)
                    .submissionId(submissionId)
                    .interviewScore(interviewScore)
                    .examScore(examScore)
                    .build();
        }).collect(Collectors.toList());
    }

    // =============================================================
    // 🔥 Các method tối thiểu để Recruiter & Admin sử dụng
    // =============================================================

    /**
     * Cập nhật trạng thái hồ sơ ứng tuyển
     */
    public Application updateApplicationStatus(Long appId, String newStatus) {
        Application app = applicationRepository.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // kiểm tra enum hợp lệ
        app.setStatus(ApplicationStatus.valueOf(newStatus));
        return applicationRepository.save(app);
    }

    /**
     * Lên lịch phỏng vấn
     */
    public Interview scheduleInterview(Long applicationId, Interview interview) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        interview.setApplication(app);
        return interviewRepository.save(interview);
    }

    /**
     * Chấm điểm phỏng vấn
     */
    public Interview scoreInterview(Long interviewId, double score) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setScore(score);
        return interviewRepository.save(interview);
    }

    /**
     * Lấy bài thi mới nhất của ứng viên
     */
    public Submission getLatestSubmissionForApplication(Long appId) {
        return submissionRepository
                .findFirstByApplication_IdOrderByCreatedAtDesc(appId)
                .orElse(null);
    }
}
