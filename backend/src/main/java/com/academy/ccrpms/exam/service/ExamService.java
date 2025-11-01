package com.academy.ccrpms.exam.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.entity.*;
import com.academy.ccrpms.exam.repository.*;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public Submission submitTest(Long userId, Long testId, Map<Long, String> answers) {
        System.out.println(">>> submitTest() called for userId=" + userId + ", testId=" + testId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        Application app = applicationRepository.findByUser(user).stream()
                .findFirst()
                .orElse(null);

        List<Question> questions = questionRepository.findAll();

        int total = 0;
        int correct = 0;

        for (Question q : questions) {
            if (q.getTest().getId().equals(testId)) {
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
                .test(test)
                .application(app)
                .score(score)
                .build();

        return submissionRepository.save(submission);
    }
}
