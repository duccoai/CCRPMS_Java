package com.academy.ccrpms.recruiter.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InterviewResponseDTO {

    private Long id;

    private LocalDateTime date;

    private String location;

    private String note;

    private String status;
}
