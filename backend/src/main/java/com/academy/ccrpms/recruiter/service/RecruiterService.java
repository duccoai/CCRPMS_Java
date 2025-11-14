package com.academy.ccrpms.recruiter.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.recruiter.dto.ApplicationSummaryDTO;
import com.academy.ccrpms.recruiter.dto.InterviewUpdateDTO;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.repository.InterviewRepository;
import com.academy.ccrpms.exam.dto.SubmissionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final SubmissionRepository submissionRepository;

    public List<ApplicationSummaryDTO> getAllApplications(Long recruiterId) {
        List<Application> apps = applicationRepository.findByJob_Recruiter_Id(recruiterId);

        return apps.stream().map(app -> {
            Long applicationId = app.getId();
            String candidateUsername = app.getUser() != null ? app.getUser().getUsername() : null;
            String candidateFullName = app.getUser() != null ? app.getUser().getFullName() : null;
            String candidateEmail = app.getUser() != null ? app.getUser().getEmail() : null;
            String jobTitle = app.getJob() != null ? app.getJob().getTitle() : null;
            String status = app.getStatus() != null ? app.getStatus().name() : null;

            Optional<Interview> interviewOpt = interviewRepository.findFirstByApplicationOrderByInterviewDateDesc(app);
            Long interviewId = interviewOpt.map(Interview::getId).orElse(null);
            Double interviewScore = interviewOpt.map(Interview::getScore).orElse(null);

            Optional<Submission> submissionOpt = submissionRepository.findFirstByApplication_IdOrderByCreatedAtDesc(applicationId);
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

    @Transactional
    public ApplicationSummaryDTO updateApplicationStatus(Long applicationId, String status) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));
        Application saved = applicationRepository.save(app);

        Optional<Interview> interviewOpt = interviewRepository.findFirstByApplicationOrderByInterviewDateDesc(saved);
        Double interviewScore = interviewOpt.map(Interview::getScore).orElse(null);
        Double examScore = submissionRepository.findFirstByApplication_IdOrderByCreatedAtDesc(saved.getId())
                .map(Submission::getScore).orElse(null);

        return ApplicationSummaryDTO.builder()
                .applicationId(saved.getId())
                .candidateFullName(saved.getUser() != null ? saved.getUser().getFullName() : null)
                .candidateEmail(saved.getUser() != null ? saved.getUser().getEmail() : null)
                .jobTitle(saved.getJob() != null ? saved.getJob().getTitle() : null)
                .status(saved.getStatus() != null ? saved.getStatus().name() : null)
                .interviewScore(interviewScore)
                .examScore(examScore)
                .build();
    }

    @Transactional
    public Interview updateInterview(InterviewUpdateDTO dto) {
        Application app = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Interview interview = interviewRepository.findByApplication(app)
                .orElseGet(() -> {
                    Interview i = new Interview();
                    i.setApplication(app);
                    return i;
                });

        if (dto.getStatus() != null) interview.setStatus(Interview.InterviewStatus.valueOf(dto.getStatus().toUpperCase()));
        if (dto.getSchedule() != null) interview.setInterviewDate(dto.getSchedule().toLocalDateTime());
        if (dto.getScoreInterview() != null) interview.setScore(dto.getScoreInterview());
        if (dto.getScoreExam() != null) interview.setScoreExam(dto.getScoreExam());
        if (dto.getComment() != null) interview.setComment(dto.getComment());
        if (dto.getLocation() != null) interview.setLocation(dto.getLocation());

        return interviewRepository.save(interview);
    }

    @Transactional
    public Interview scoreInterview(Long id, Double score, String comment) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        interview.setScore(score);
        interview.setComment(comment);
        interview.setStatus(Interview.InterviewStatus.COMPLETED);
        return interviewRepository.save(interview);
    }

    @Transactional
    public void scoreSubmission(Long submissionId, Double score) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setScore(score);
        submissionRepository.save(submission);
    }

    public Submission getLatestSubmissionForApplication(Long applicationId) {
        return submissionRepository.findFirstByApplication_IdOrderByCreatedAtDesc(applicationId).orElse(null);
    }

    public SubmissionResponseDTO toSubmissionDTO(Submission s) {
        if (s == null) return null;
        return SubmissionResponseDTO.builder()
                .submissionId(s.getId())
                .userId(s.getUser() != null ? s.getUser().getId() : null)
                .applicationId(s.getApplication() != null ? s.getApplication().getId() : null)
                .score(s.getScore())
                .createdAt(s.getCreatedAt())
                .answers(s.getAnswers()) // hoặc đổi theo field trong entity
                .build();
    }
}
