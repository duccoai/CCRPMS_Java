package com.academy.ccrpms.exam.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.entity.*;
import com.academy.ccrpms.exam.repository.*;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public Submission submitExam(Long userId, Long examId, Map<Long, String> answers) {
        System.out.println(">>> submitExam() called for userId=" + userId + ", examId=" + examId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Application app = applicationRepository.findByUser(user).stream()
                .findFirst()
                .orElse(null);

        List<Question> questions = questionRepository.findAll();

        int total = 0;
        int correct = 0;

        for (Question q : questions) {
            if (q.getExam().getId().equals(examId)) {
                total++;
                String userAnswer = answers.get(q.getId());
                if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getCorrectAnswer())) {
                    correct++;
                }
            }
        }

        double score = (double) correct / total * 100.0;

        Submission submission = Submission.builder()
                .user(user)
                .exam(exam)
                .application(app)
                .score(score)
                .build();

        return submissionRepository.save(submission);
    }

        // 🟩 Hàm lấy dữ liệu bài thi
    public Map<String, Object> startExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        // Lấy danh sách câu hỏi của bài thi
        List<Question> questions = questionRepository.findByExamId(examId);

        // Đóng gói dữ liệu trả về
        Map<String, Object> response = new HashMap<>();
        response.put("examId", exam.getId());
        response.put("title", exam.getTitle());
        response.put("questions", questions);

        return response;
    }
}

