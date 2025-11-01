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
    @PostMapping("/submit/{userId}/{testId}")
    public ResponseEntity<SubmissionResponseDTO> submitTest(
            @PathVariable Long userId,
            @PathVariable Long testId,
            @RequestBody(required = false) Map<Long, String> answers // 👈 thêm tham số này
    ) {
        Submission submission = examService.submitTest(userId, testId, answers);

        SubmissionResponseDTO dto = SubmissionResponseDTO.builder()
                .submissionId(submission.getId())
                .score(submission.getScore())
                .testTitle(submission.getTest().getTitle())
                .testDescription(submission.getTest().getDescription())
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
}
