package com.academy.ccrpms.application.controller;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // Nộp hồ sơ
    @PostMapping("/submit")
    public ResponseEntity<Application> submit(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        String jobTitle = request.get("jobTitle").toString();

        Application app = applicationService.submitApplication(userId, jobTitle);
        return ResponseEntity.ok(app);
    }

    // Xem danh sách hồ sơ đã nộp
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getUserApplications(@PathVariable Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUser(userId));
    }
}
