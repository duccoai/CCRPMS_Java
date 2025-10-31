package com.academy.ccrpms.application.controller;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // Nộp hồ sơ
    @PostMapping("/submit/{userId}/{jobId}")
    public ResponseEntity<Application> submit(
            @PathVariable Long userId,
            @PathVariable Long jobId
    ) {
        Application app = applicationService.submitApplication(userId, jobId);
        return ResponseEntity.ok(app);
    }

    // Xem danh sách hồ sơ của ứng viên
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getUserApplications(@PathVariable Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUser(userId));
    }
}
