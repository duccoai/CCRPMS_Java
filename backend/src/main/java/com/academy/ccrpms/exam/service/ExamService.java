package com.academy.ccrpms.exam.service;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.repository.ApplicationRepository;
import com.academy.ccrpms.exam.entity.Answer;
import com.academy.ccrpms.exam.entity.Exam;
import com.academy.ccrpms.exam.entity.Question;
import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.exam.repository.AnswerRepository;
import com.academy.ccrpms.exam.repository.ExamRepository;
import com.academy.ccrpms.exam.repository.QuestionRepository;
import com.academy.ccrpms.exam.repository.SubmissionRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    // -----------------------------------------------------
    // 1) Nộp bài + chấm điểm
    // -----------------------------------------------------
    public Submission submitExam(Long userId, Long examId, Map<Long, String> userAnswers) {

        if (userAnswers == null) userAnswers = new HashMap<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Application application = applicationRepository.findByCandidate(user)
                .stream().findFirst().orElse(null);

        List<Question> questions = questionRepository.findByExamId(examId);

        int total = questions.size();

        long correct = questions.stream()
                .filter(q ->
                        userAnswers.containsKey(q.getId()) &&
                        userAnswers.get(q.getId()).equalsIgnoreCase(q.getCorrectAnswer())
                )
                .count();

        double score = (total > 0) ? (double) correct / total * 100 : 0;

        Submission submission = submissionRepository.save(
                Submission.builder()
                        .user(user)
                        .exam(exam)
                        .application(application)
                        .score(score)
                        .build()
        );

        // Lưu từng câu trả lời
        for (Question q : questions) {
            String chosen = userAnswers.get(q.getId());

            Answer ans = Answer.builder()
                    .question(q)
                    .submission(submission)
                    .text(chosen)
                    .build();

            answerRepository.save(ans);
        }

        return submission;
    }

    // -----------------------------------------------------
    // 2) Load đề thi (ĐÃ SỬA THEO YÊU CẦU)
    // -----------------------------------------------------
    public Map<String, Object> startExam(Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        List<Question> questions = questionRepository.findByExamId(examId);

        // Convert Question -> Map với fallback ""
        List<Map<String, Object>> questionList = questions.stream()
                .map(q -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", q.getId());
                    m.put("content", q.getContent());
                    m.put("optionA", q.getOptionA() != null ? q.getOptionA() : "");
                    m.put("optionB", q.getOptionB() != null ? q.getOptionB() : "");
                    m.put("optionC", q.getOptionC() != null ? q.getOptionC() : "");
                    m.put("optionD", q.getOptionD() != null ? q.getOptionD() : "");
                    m.put("correctAnswer", q.getCorrectAnswer() != null ? q.getCorrectAnswer() : "");
                    return m;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("examId", exam.getId());
        response.put("title", exam.getTitle());
        response.put("description", exam.getDescription());
        response.put("questions", questionList);

        return response;
    }
}
