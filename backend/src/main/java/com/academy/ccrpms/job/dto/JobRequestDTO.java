package com.academy.ccrpms.job.dto;

import com.academy.ccrpms.job.entity.JobStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDTO {
    private String title;
    private String description;
    private String location;
    private String salaryRange;
    private JobStatus status;
    private Long recruiterId; // optional, nếu client gửi recruiter id
}
