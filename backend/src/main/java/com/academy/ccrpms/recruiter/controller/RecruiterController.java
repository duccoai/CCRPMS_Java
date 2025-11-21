package com.academy.ccrpms.recruiter.controller;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.job.dto.JobRequestDTO;
import com.academy.ccrpms.job.dto.JobResponseDTO;
import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.job.repository.JobRepository;
import com.academy.ccrpms.recruiter.dto.ApplicationSummaryDTO;
import com.academy.ccrpms.recruiter.dto.InterviewScheduleDTO;
import com.academy.ccrpms.recruiter.dto.InterviewUpdateDTO;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.service.RecruiterService;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import com.academy.ccrpms.promotion.entity.PromotionApplication;
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

    // 1️⃣ Lấy danh sách applications
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationSummaryDTO>> getApplications(Authentication authentication) {
        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
        Long recruiterId = cud.getUser().getId();
        List<ApplicationSummaryDTO> applications = recruiterService.getAllApplications(recruiterId);
        return ResponseEntity.ok(applications);
    }

    // 2️⃣ Cập nhật trạng thái application
    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApplicationSummaryDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        ApplicationSummaryDTO updated = recruiterService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    // 3️⃣ Tạo lịch phỏng vấn
    @PostMapping("/interviews/schedule")
    public ResponseEntity<Interview> scheduleInterview(@RequestBody InterviewScheduleDTO dto) {
        Interview interview = dto.toEntity();
        Interview saved = recruiterService.scheduleInterview(dto.getApplicationId(), interview);
        return ResponseEntity.ok(saved);
    }

    // 4️⃣ Cập nhật lịch phỏng vấn
    @PutMapping("/interviews")
    public ResponseEntity<Interview> updateInterview(@RequestBody InterviewUpdateDTO dto) {
        Interview interview = dto.toEntity();
        Interview updated = recruiterService.updateInterview(interview);
        return ResponseEntity.ok(updated);
    }

    // 5️⃣ Chấm điểm phỏng vấn
    @PostMapping("/interviews/{id}/score")
    public ResponseEntity<Interview> scoreInterview(
            @PathVariable Long id,
            @RequestParam Double score
    ) {
        Interview updated = recruiterService.scoreInterview(id, score);
        return ResponseEntity.ok(updated);
    }

    // 6️⃣ Chấm điểm bài thi của candidate
    @PostMapping("/submissions/{id}/score")
    public ResponseEntity<Void> scoreSubmission(@PathVariable Long id, @RequestParam Double score) {
        recruiterService.scoreSubmission(id, score);
        return ResponseEntity.ok().build();
    }

    // 7️⃣ Lấy danh sách jobs
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

    // 8️⃣ Tạo job mới
    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(Authentication authentication, @RequestBody JobRequestDTO dto) {
        if (dto == null || dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Field 'title' is required");
        }

        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
        Long recruiterId = cud.getUser().getId();

        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalaryRange(dto.getSalaryRange());
        job.setStatus(dto.getStatus());
        job.setRecruiter(recruiter);

        Job saved = jobRepository.save(job);
        JobResponseDTO out = JobResponseDTO.fromEntity(saved);
        return ResponseEntity.ok(out);
    }

    // 9️⃣ Lấy danh sách promotion applications (mới)
    @GetMapping("/promotions")
    public ResponseEntity<?> getPromotionApplications() {
        try {
            List<PromotionApplication> promotions = recruiterService.getAllPromotionApplications();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            e.printStackTrace(); // log lỗi server
            return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
        }
    }
}
