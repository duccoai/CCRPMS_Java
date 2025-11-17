package com.academy.ccrpms.exam.controller;

import com.academy.ccrpms.exam.dto.SubmissionResponseDTO;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    private final SubmissionRepository submissionRepository;

    @PostMapping("/submit/{userId}/{examId}")
    public ResponseEntity<?> submitExam(
            @PathVariable Long userId,
            @PathVariable Long examId,
            @RequestBody Map<Long, String> answers
    ) {
        Submission submission = examService.submitExam(userId, examId, answers);

        return ResponseEntity.ok(
                SubmissionResponseDTO.builder()
                        .submissionId(submission.getId())
                        .score(submission.getScore())
                        .examDescription(submission.getExam().getDescription())
                        .userName(submission.getUser().getFullName())
                        .userEmail(submission.getUser().getEmail())
                        .applicationStatus(
                                submission.getApplication() != null
                                        ? submission.getApplication().getStatus().name()
                                        : "PENDING"
                        )
                        .build()
        );
    }

    @GetMapping("/start/{examId}")
    public ResponseEntity<?> startExam(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.startExam(examId));
    }

    @GetMapping("/result/{userId}")
    public ResponseEntity<?> getResults(@PathVariable Long userId) {

        List<Submission> submissions = submissionRepository.findByUser_Id(userId);

        List<Map<String, Object>> results = submissions.stream()
                .map(s -> Map.of(
                        "title", s.getExam().getTitle(),
                        "score", s.getScore(),
                        "status", s.getApplication() != null ?
                                s.getApplication().getStatus().name() :
                                "PENDING"
                )).toList();

        return ResponseEntity.ok(results);
    }
}
