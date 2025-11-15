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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // Nộp hồ sơ → chỉ cần jobId, candidate lấy từ token
    @PostMapping("/submit/{jobId}")
    public ResponseEntity<ApplicationResponseDTO> submit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long jobId
    ) {
        Application app = applicationService.submitApplication(userDetails.getId(), jobId);
        ApplicationResponseDTO dto = ApplicationResponseDTO.fromEntity(app);
        return ResponseEntity.ok(dto);
    }


    // Lấy hồ sơ user hiện tại
    @GetMapping("/user/me")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        Long userId = userDetails.getUser().getId();
        List<Application> apps = applicationService.getApplicationsByUser(userId);
        List<ApplicationResponseDTO> dtos = apps.stream()
                .map(ApplicationResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}