package com.academy.ccrpms.exam.controller;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.exam.dto.SubmissionResponseDTO;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.exam.service.ExamService;
import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.promotion.entity.PromotionStatus;
import com.academy.ccrpms.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    private final SubmissionRepository submissionRepository;
    private final PromotionService promotionService;

    // ======================================================
    // Start exam (Candidate only allowed if APPROVED)
    // ======================================================
    @GetMapping("/start/{examId}")
    public ResponseEntity<?> startExam(
            @PathVariable Long examId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(401).body("Unauthorized");
            }

            // Lấy hồ sơ nâng bậc gần nhất
            PromotionApplication latestPromotion =
                    promotionService.getLatestApplicationByCandidate(userDetails.getId());

            if (latestPromotion == null) {
                return ResponseEntity.status(403)
                        .body("Bạn chưa nộp hồ sơ nâng bậc. Không thể làm bài thi online.");
            }

            if (!PromotionStatus.APPROVED.equals(latestPromotion.getStatus())) {
                return ResponseEntity.status(403)
                        .body("Hồ sơ nâng bậc chưa được duyệt. Không thể làm bài thi online.");
            }

            // Nếu đã APPROVED → trả về đề thi
            return ResponseEntity.ok(examService.startExam(examId));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Server error: " + e.getMessage());
        }
    }

    // ======================================================
    // Submit exam
    // ======================================================
    @PostMapping("/submit/{userId}/{examId}")
    public ResponseEntity<?> submitExam(
            @PathVariable Long userId,
            @PathVariable Long examId,
            @RequestBody Map<String, Object> body
    ) {
        try {
            Map<String, Object> rawAnswers = (Map<String, Object>) body.get("answers");
            Map<String, String> answers = new HashMap<>();

            if (rawAnswers != null) {
                rawAnswers.forEach((k, v) ->
                        answers.put(k.toString(), v != null ? v.toString() : null)
                );
            }

            Long applicationId = body.get("applicationId") != null
                    ? Long.valueOf(body.get("applicationId").toString())
                    : null;

            Submission submission = examService.submitExam(userId, examId, answers, applicationId);

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
                                            : PromotionStatus.PENDING.name()
                            )
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Submit exam failed: " + e.getMessage());
        }
    }

    // ======================================================
    // Get results
    // ======================================================
    @GetMapping("/result/{userId}")
    public ResponseEntity<?> getResults(@PathVariable Long userId) {
        try {
            List<Submission> submissions = submissionRepository.findByUser_Id(userId);

            List<Map<String, Object>> results = new ArrayList<>();
            for (Submission s : submissions) {
                results.add(Map.of(
                        "title", s.getExam().getTitle(),
                        "score", s.getScore(),
                        "status", s.getApplication() != null
                                ? s.getApplication().getStatus().name()
                                : PromotionStatus.PENDING.name()
                ));
            }
            return ResponseEntity.ok(results);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Cannot get results: " + e.getMessage());
        }
    }
}
