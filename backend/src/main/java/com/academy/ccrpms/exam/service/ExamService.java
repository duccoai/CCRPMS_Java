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
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    // ---------------------------
    // Submit exam + update Application
    // ---------------------------
    @Transactional
    public Submission submitExam(Long userId, Long examId, Map<String, String> answers, Long applicationId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        if (answers == null) answers = new HashMap<>();

        // Tính điểm
        int score = calculateScore(exam, answers);

        // Tạo submission
        Submission submission = Submission.builder()
                .user(user)
                .exam(exam)
                .score(score)
                .submittedAt(LocalDateTime.now())
                .answersJson(convertAnswersToJson(answers))
                .build();

        // Nếu có application → gắn và cập nhật điểm
        if (applicationId != null) {
            Application app = applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            submission.setApplication(app);

            // Cập nhật điểm vào application
            app.setScoreExam((double) score);
            applicationRepository.save(app);
        }

        submission = submissionRepository.save(submission);

        // Lưu từng answer
        saveAnswersEntities(submission, answers);

        return submission;
    }

    // ---------------------------
    // Start exam
    // ---------------------------
    public Map<String, Object> startExam(Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        List<Question> questions = questionRepository.findByExamId(examId);

        List<Map<String, Object>> qList = new ArrayList<>();
        for (Question q : questions) {
            Map<String, Object> qMap = new HashMap<>();
            qMap.put("id", q.getId());
            qMap.put("content", q.getContent());
            qMap.put("optionA", q.getOptionA() != null ? q.getOptionA() : "");
            qMap.put("optionB", q.getOptionB() != null ? q.getOptionB() : "");
            qMap.put("optionC", q.getOptionC() != null ? q.getOptionC() : "");
            qMap.put("optionD", q.getOptionD() != null ? q.getOptionD() : "");
            qList.add(qMap);
        }

        return Map.of(
                "id", exam.getId(),
                "title", exam.getTitle(),
                "description", exam.getDescription(),
                "questions", qList
        );
    }

    // ---------------------------
    // Calculate score
    // ---------------------------
    private int calculateScore(Exam exam, Map<String, String> answers) {
        List<Question> questions = questionRepository.findByExamId(exam.getId());
        if (questions.isEmpty()) return 0;

        long correct = questions.stream()
                .filter(q -> {
                    String ans = answers.get(String.valueOf(q.getId()));
                    return ans != null && ans.equalsIgnoreCase(q.getCorrectAnswer());
                })
                .count();

        return (int) Math.round((double) correct / questions.size() * 100);
    }

    // ---------------------------
    // Convert answers → JSON string
    // ---------------------------
    private String convertAnswersToJson(Map<String, String> answers) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(answers);
        } catch (Exception e) {
            return "{}";
        }
    }

    // ---------------------------
    // Save Answer entities
    // ---------------------------
    private void saveAnswersEntities(Submission submission, Map<String, String> answers) {
        answers.forEach((qidStr, userAns) -> {
            Long qid = Long.valueOf(qidStr);
            Question q = questionRepository.findById(qid).orElse(null);
            if (q == null) return;

            Answer ans = Answer.builder()
                    .submission(submission)
                    .question(q)
                    .text(userAns)
                    .build();
            answerRepository.save(ans);
        });
    }
}
