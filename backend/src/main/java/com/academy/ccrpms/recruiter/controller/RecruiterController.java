package com.academy.ccrpms.recruiter.controller;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.job.dto.JobRequestDTO;
import com.academy.ccrpms.job.dto.JobResponseDTO;
import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.recruiter.dto.ApplicationSummaryDTO;
import com.academy.ccrpms.recruiter.dto.InterviewUpdateDTO;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.service.RecruiterService;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import com.academy.ccrpms.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService recruiterService;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    // 1. Xem danh sách hồ sơ của recruiter hiện tại
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationSummaryDTO>> getApplications(Authentication authentication) {
        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
        Long recruiterId = cud.getUser().getId();
        return ResponseEntity.ok(recruiterService.getAllApplications(recruiterId));
    }

    // 2. Cập nhật trạng thái hồ sơ
    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApplicationSummaryDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam("status") String status
    ) {
        ApplicationSummaryDTO dto = recruiterService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(dto);
    }

    // 3. Schedule or update interview
    @PostMapping("/interviews/schedule")
    public ResponseEntity<Interview> scheduleInterview(@RequestBody InterviewUpdateDTO dto) {
        Interview iv = recruiterService.updateInterview(dto);
        return ResponseEntity.ok(iv);
    }

    // 4. Score interview by interviewId
    @PostMapping("/interviews/{id}/score")
    public ResponseEntity<Interview> scoreInterview(
            @PathVariable Long id,
            @RequestParam Double score,
            @RequestParam(required = false) String comment
    ) {
        Interview iv = recruiterService.scoreInterview(id, score, comment);
        return ResponseEntity.ok(iv);
    }

    // 5. Score submission (bài thi)
    @PostMapping("/submissions/{id}/score")
    public ResponseEntity<?> scoreSubmission(@PathVariable Long id, @RequestParam Double score) {
        try {
            recruiterService.scoreSubmission(id, score);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(Map.of("error", "SubmissionNotFound", "message", ex.getMessage()));
        }
    }

    // 6. Lấy jobs của recruiter hiện tại
    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponseDTO>> getMyJobs(Authentication authentication) {
        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
        Long recruiterId = cud.getUser().getId();
        List<Job> jobs = jobRepository.findByRecruiter_Id(recruiterId);

        List<JobResponseDTO> dtos = jobs.stream()
                .map(JobResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // 7. Tạo job mới
    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(Authentication authentication, @RequestBody JobRequestDTO dto) {
        if (dto == null || dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Field 'title' is required");
        }

        try {
            CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
            Long recruiterId = cud.getUser().getId();

            User recruiter = userRepository.findById(recruiterId)
                    .orElseThrow(() -> new RuntimeException("Recruiter not found"));

            Job job = new Job();
            job.setTitle(dto.getTitle());
            job.setDescription(dto.getDescription());
            job.setLocation(dto.getLocation());
            job.setSalaryRange(dto.getSalaryRange());
            if (dto.getStatus() != null) job.setStatus(dto.getStatus());
            job.setRecruiter(recruiter);

            Job saved = jobRepository.save(job);
            JobResponseDTO out = JobResponseDTO.fromEntity(saved);
            return ResponseEntity.ok(out);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Failed to create job: " + ex.getMessage());
        }
    }

    // 8. Lấy submission theo applicationId (chi tiết + answers)
    @GetMapping("/applications/{applicationId}/submission")
    public ResponseEntity<?> getSubmissionForApplication(@PathVariable Long applicationId) {
        var submission = recruiterService.getLatestSubmissionForApplication(applicationId);
        if (submission == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "SubmissionNotFound",
                    "message", "Không tìm thấy submission cho application này"
            ));
        }
        var dto = recruiterService.toSubmissionDTO(submission);
        return ResponseEntity.ok(dto);
    }
}
