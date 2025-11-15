package com.academy.ccrpms.admin.controller;

import com.academy.ccrpms.admin.dto.AdminStatsDTO;
import com.academy.ccrpms.admin.dto.UserDTO;
import com.academy.ccrpms.admin.service.AdminService;
import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.exam.entity.Exam;
import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ----------------- User Management -----------------
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsersDTO());
    }


    @PostMapping("/users/recruiter")
    public ResponseEntity<User> createRecruiter(@RequestBody User user) {
        return ResponseEntity.ok(adminService.createRecruiter(user));
    }

    @PostMapping("/users/admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        return ResponseEntity.ok(adminService.createAdmin(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User data) {
        return ResponseEntity.ok(adminService.updateUser(id, data));
    }

    // ----------------- Application Management -----------------
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(adminService.getAllApplications());
    }

    @PutMapping("/applications/{id}/final")
    public ResponseEntity<Application> updateFinalDecision(@PathVariable Long id, @RequestParam String result) {
        return ResponseEntity.ok(adminService.updateFinalDecision(id, result));
    }

    // ----------------- Interview & Exam -----------------
    @GetMapping("/interviews")
    public ResponseEntity<List<Interview>> getAllInterviews() {
        return ResponseEntity.ok(adminService.getAllInterviews());
    }

    @PutMapping("/exams/{id}/toggle")
    public ResponseEntity<Exam> toggleExam(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.toggleExam(id));
    }

    // ----------------- Reports -----------------
    @GetMapping("/statistics")
    public ResponseEntity<AdminStatsDTO> getStatistics() {
        return ResponseEntity.ok(adminService.getStatistics());
    }
}
