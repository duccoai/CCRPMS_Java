package com.academy.ccrpms.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminStatsDTO {
    private long totalCandidates;
    private long totalApplications;
    private long passed;
    private long failed;
}
