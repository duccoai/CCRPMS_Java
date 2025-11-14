package com.academy.ccrpms.job.service;

import com.academy.ccrpms.job.dto.JobRequestDTO;
import com.academy.ccrpms.job.dto.JobResponseDTO;
import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.job.entity.JobStatus;
import com.academy.ccrpms.job.repository.JobRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    // --- Entity-based methods (kept for internal use if needed) ---
    public Job createJobEntity(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobsEntities() {
        return jobRepository.findAll();
    }

    public Job getJobEntityById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job updateJobEntity(Long id, Job updated) {
        Job job = getJobEntityById(id);
        job.setTitle(updated.getTitle());
        job.setDescription(updated.getDescription());
        job.setLocation(updated.getLocation());
        job.setSalaryRange(updated.getSalaryRange());
        job.setStatus(updated.getStatus());
        // recruiter handled outside if needed
        return jobRepository.save(job);
    }

    public void deleteJobEntity(Long id) {
        jobRepository.deleteById(id);
    }

    // --- DTO-based API used by controller ---

    /**
     * Create job from JobRequestDTO and return JobResponseDTO
     */
    @Transactional
    public JobResponseDTO createJob(JobRequestDTO request) {
        Job job = new Job();
        applyRequestToJob(request, job);
        Job saved = jobRepository.save(job);
        return JobResponseDTO.fromEntity(saved);
    }

    /**
     * Get all jobs as DTOs
     */
    @Transactional(readOnly = true)
    public List<JobResponseDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(JobResponseDTO::fromEntity)
                .toList();
    }

    /**
     * Get single job by id as DTO
     */
    @Transactional(readOnly = true)
    public JobResponseDTO getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return JobResponseDTO.fromEntity(job);
    }

    /**
     * Update job from request DTO
     */
    @Transactional
    public JobResponseDTO updateJob(Long id, JobRequestDTO request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        applyRequestToJob(request, job);
        Job saved = jobRepository.save(job);
        return JobResponseDTO.fromEntity(saved);
    }

    @Transactional
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // --- Helper: apply request fields to entity ---
    private void applyRequestToJob(JobRequestDTO request, Job job) {
        if (request == null) return;

        if (request.getTitle() != null) job.setTitle(request.getTitle());
        if (request.getDescription() != null) job.setDescription(request.getDescription());
        if (request.getLocation() != null) job.setLocation(request.getLocation());
        if (request.getSalaryRange() != null) job.setSalaryRange(request.getSalaryRange());

        if (request.getStatus() != null) {
            // request.status is already JobStatus enum (as per DTO)
            job.setStatus(request.getStatus());
        }

        if (request.getRecruiterId() != null) {
            User recruiter = userRepository.findById(request.getRecruiterId())
                    .orElseThrow(() -> new RuntimeException("Recruiter (User) not found with id: " + request.getRecruiterId()));
            job.setRecruiter(recruiter);
        }
    }
}
