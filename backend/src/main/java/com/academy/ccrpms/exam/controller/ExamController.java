package com.academy.ccrpms.exam.controller;

import com.academy.ccrpms.exam.dto.SubmissionResponseDTO;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    // ✅ Nộp bài thi và chấm điểm
    @PostMapping("/submit/{userId}/{examId}")
    public ResponseEntity<SubmissionResponseDTO> submitExam(
            @PathVariable Long userId,
            @PathVariable Long examId,
            @RequestBody(required = false) Map<Long, String> answers // 👈 câu trả lời từ client
    ) {
        Submission submission = examService.submitExam(userId, examId, answers);

        SubmissionResponseDTO dto = SubmissionResponseDTO.builder()
                .submissionId(submission.getId())
                .score(submission.getScore())
                .examDescription(submission.getExam().getDescription())
                .userName(submission.getUser().getFullName())
                .userEmail(submission.getUser().getEmail())
                .applicationStatus(
                        submission.getApplication() != null && submission.getApplication().getStatus() != null
                                ? submission.getApplication().getStatus().name()
                                : "PENDING"
                )
                .build();

        return ResponseEntity.ok(dto);
    }

    // ✅ Bắt đầu làm bài thi
    @GetMapping("/start/{examId}")
    public ResponseEntity<?> startExam(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.startExam(examId));
    }
}
