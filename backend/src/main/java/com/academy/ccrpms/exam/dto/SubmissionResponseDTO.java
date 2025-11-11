package com.academy.ccrpms.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponseDTO {

    private Long submissionId;        // ID của bài nộp
    private double score;             // Điểm
    private String examDescription;   // Mô tả bài thi
    private String userName;          // Tên thí sinh
    private String userEmail;         // Email thí sinh
    private String applicationStatus; // Trạng thái hồ sơ
}
