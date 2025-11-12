package com.academy.ccrpms.recruiter.controller;

import com.academy.ccrpms.recruiter.dto.ApplicationSummaryDTO;
import com.academy.ccrpms.recruiter.dto.InterviewScheduleDTO;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.recruiter.service.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService recruiterService;

    // 🧾 1. Xem danh sách hồ sơ
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationSummaryDTO>> getApplications() {
        return ResponseEntity.ok(recruiterService.getAllApplications());
    }

    // ✅ 2. Duyệt / từ chối hồ sơ (endpoint rõ ràng với param status)
    @PutMapping("/applications/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam("status") String status
    ) {
        return ResponseEntity.ok(recruiterService.updateApplicationStatus(id, status));
    }

    // 🗓️ 3. Lên lịch phỏng vấn
    @PostMapping("/interviews/schedule")
    public ResponseEntity<Interview> scheduleInterview(@RequestBody InterviewScheduleDTO dto) {
        return ResponseEntity.ok(recruiterService.scheduleInterview(dto));
    }

    // 🧮 4. Chấm điểm phỏng vấn
    @PostMapping("/interviews/{id}/score")
    public ResponseEntity<Interview> scoreInterview(
            @PathVariable Long id,
            @RequestParam Double score,
            @RequestParam(required = false) String comment
    ) {
        return ResponseEntity.ok(recruiterService.scoreInterview(id, score, comment));
    }
}
