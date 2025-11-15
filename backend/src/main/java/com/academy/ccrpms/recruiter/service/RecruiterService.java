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

    public List<ApplicationSummaryDTO> getAllApplications(Long recruiterId) {
        List<Application> apps = applicationRepository.findByJob_Recruiter_Id(recruiterId);

        return apps.stream().map(app -> {
            Optional<Interview> interviewOpt =
                    interviewRepository.findFirstByApplicationOrderByInterviewDateDesc(app);
            Optional<Submission> submissionOpt =
                    submissionRepository.findFirstByApplication_IdOrderByCreatedAtDesc(app.getId());

            return ApplicationSummaryDTO.builder()
                    .applicationId(app.getId())
                    .candidateUsername(app.getCandidate() != null ? app.getCandidate().getUsername() : "N/A")
                    .candidateFullName(app.getCandidate() != null ? app.getCandidate().getFullName() : "N/A")
                    .candidateEmail(app.getCandidate() != null ? app.getCandidate().getEmail() : "N/A")
                    .jobTitle(app.getJob() != null ? app.getJob().getTitle() : "N/A")
                    .status(app.getStatus() != null ? app.getStatus().name() : "PENDING")
                    .interviewId(interviewOpt.map(Interview::getId).orElse(null))
                    .interviewScore(interviewOpt.map(Interview::getScore).orElse(null))
                    .submissionId(submissionOpt.map(Submission::getId).orElse(null))
                    .examScore(submissionOpt.map(Submission::getScore).orElse(null))
                    .build();
        }).collect(Collectors.toList());
    }

    public ApplicationSummaryDTO updateApplicationStatus(Long appId, String newStatus) {
        Application app = applicationRepository.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(ApplicationStatus.valueOf(newStatus));
        Application saved = applicationRepository.save(app);

        // Trả về DTO
        return ApplicationSummaryDTO.builder()
                .applicationId(saved.getId())
                .candidateUsername(saved.getCandidate() != null ? saved.getCandidate().getUsername() : "N/A")
                .candidateFullName(saved.getCandidate() != null ? saved.getCandidate().getFullName() : "N/A")
                .candidateEmail(saved.getCandidate() != null ? saved.getCandidate().getEmail() : "N/A")
                .jobTitle(saved.getJob() != null ? saved.getJob().getTitle() : "N/A")
                .status(saved.getStatus().name())
                .build();
    }

    public Interview scheduleInterview(Long applicationId, Interview interview) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        interview.setApplication(app);
        return interviewRepository.save(interview);
    }

    public Interview updateInterview(Interview interview) {
        Interview existing = interviewRepository.findById(interview.getId())
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        existing.setInterviewDate(interview.getInterviewDate());
        existing.setLocation(interview.getLocation());
        existing.setNotes(interview.getNotes());
        return interviewRepository.save(existing);
    }

    public Interview scoreInterview(Long interviewId, double score) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        interview.setScore(score);
        return interviewRepository.save(interview);
    }

    public void scoreSubmission(Long submissionId, double score) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setScore(score);
        submissionRepository.save(submission);
    }
}
