package com.academy.ccrpms.recruiter.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.recruiter.dto.ApplicationSummaryDTO;
import com.academy.ccrpms.recruiter.dto.InterviewScheduleDTO;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.repository.InterviewRepository;
import com.academy.ccrpms.auth.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final SubmissionRepository submissionRepository;

    // 🧾 1. Xem danh sách hồ sơ ứng viên
    public List<ApplicationSummaryDTO> getAllApplications() {
        List<Application> apps = applicationRepository.findAll();

        return apps.stream()
                .map(app -> {
                    Double examScore = submissionRepository.findByApplication(app)
                            .stream()
                            .findFirst()
                            .map(s -> s.getScore())
                            .orElse(null);

                    Double interviewScore = interviewRepository.findByApplicationId(app.getId())
                            .stream()
                            .findFirst()
                            .map(Interview::getScore)
                            .orElse(null);

                    return ApplicationSummaryDTO.builder()
                            .applicationId(app.getId())
                            .candidateName(app.getUser().getFullName())
                            .candidateEmail(app.getUser().getEmail())
                            .jobTitle(app.getJob().getTitle())
                            .status(app.getStatus().name())
                            .examScore(examScore)
                            .interviewScore(interviewScore)
                            .build();
                })
                .collect(Collectors.toList());
    }

    // ✅ 2. Duyệt / từ chối hồ sơ (chỉ các application thuộc job của recruiter hiện tại)
    public Application updateApplicationStatus(Long applicationId, String status) {
        // 🔹 Lấy recruiter đang login từ token
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long recruiterId = userDetails.getUser().getId();

        // 🔹 Tìm application theo id + recruiterId
        Application app = applicationRepository.findByIdAndJob_Recruiter_Id(applicationId, recruiterId)
                .orElseThrow(() -> new RuntimeException("Application not found or not your job"));

        // 🔹 Update trạng thái
        app.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));
        return applicationRepository.save(app);
    }

    // 🗓️ 3. Lên lịch phỏng vấn
    public Interview scheduleInterview(InterviewScheduleDTO dto) {
        Application app = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Interview interview = Interview.builder()
                .application(app)
                .interviewDate(dto.getInterviewDate())
                .location(dto.getLocation())
                .note(dto.getNote())
                .status(Interview.InterviewStatus.SCHEDULED)
                .build();

        return interviewRepository.save(interview);
    }

    // 🧮 4. Chấm điểm phỏng vấn
    public Interview scoreInterview(Long interviewId, Double score, String comment) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setScore(score);
        interview.setComment(comment);
        interview.setStatus(Interview.InterviewStatus.COMPLETED);

        return interviewRepository.save(interview);
    }
}
