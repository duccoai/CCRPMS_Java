package com.academy.ccrpms.application.dto;

import com.academy.ccrpms.application.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // <--- bắt buộc để có builder()
public class ApplicationResponseDTO {
    private Long id;
    private String candidateName;
    private String candidateEmail;
    private Long jobId;
    private String jobTitle;
    private String status;
    private OffsetDateTime createdAt;

public static ApplicationResponseDTO fromEntity(Application app) {
    return ApplicationResponseDTO.builder()
            .id(app.getId())
            .candidateName(app.getCandidate() != null ? app.getCandidate().getFullName() : null)
            .candidateEmail(app.getCandidate() != null ? app.getCandidate().getEmail() : null)
            .jobId(app.getJob() != null ? app.getJob().getId() : null)
            .jobTitle(app.getJob() != null ? app.getJob().getTitle() : null)
            .status(app.getStatus() != null ? app.getStatus().name() : "PENDING")
            .createdAt(app.getCreatedAt())
            .build();
}

}
