package com.academy.ccrpms.application.controller;

import com.academy.ccrpms.application.dto.ApplicationResponseDTO;
import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.service.ApplicationService;
import com.academy.ccrpms.auth.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * Nộp hồ sơ cho một job.
     * Candidate được lấy từ token.
     *
     * @param userDetails thông tin user hiện tại
     * @param jobId       id của job muốn nộp hồ sơ
     * @return ApplicationResponseDTO của hồ sơ vừa nộp hoặc thông báo lỗi thân thiện
     */
    @PostMapping("/submit/{jobId}")
    public ResponseEntity<?> submit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long jobId
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            Application app = applicationService.submitApplication(userDetails.getId(), jobId);
            ApplicationResponseDTO dto = ApplicationResponseDTO.fromEntity(app);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException ex) {
            // Trả về thông báo thân thiện nếu nộp trùng hoặc lỗi khác
            return ResponseEntity.badRequest()
                    .body(Map.of("message", ex.getMessage()));
        }
    }

    /**
     * Lấy tất cả hồ sơ của user hiện tại.
     *
     * @param userDetails thông tin user hiện tại
     * @return danh sách ApplicationResponseDTO
     */
    @GetMapping("/user/me")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        Long userId = userDetails.getUser().getId();
        List<Application> apps = applicationService.getApplicationsByCandidate(userId);
        List<ApplicationResponseDTO> dtos = apps.stream()
                .map(ApplicationResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Lấy kết quả hồ sơ (kèm điểm exam) của một candidate cho recruiter.
     *
     * @param candidateId ID của candidate
     * @return danh sách kết quả ứng tuyển kèm điểm thi
     */
    @GetMapping("/user/{candidateId}/results")
    public ResponseEntity<List<Map<String, Object>>> getCandidateResults(
            @PathVariable Long candidateId
    ) {
        List<Map<String, Object>> results = applicationService.getApplicationResults(candidateId);
        return ResponseEntity.ok(results);
    }
}
