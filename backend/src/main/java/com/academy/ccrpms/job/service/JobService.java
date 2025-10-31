package com.academy.ccrpms.job.service;

import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job updateJob(Long id, Job updated) {
        Job job = getJobById(id);
        job.setTitle(updated.getTitle());
        job.setDescription(updated.getDescription());
        job.setLocation(updated.getLocation());
        job.setSalaryRange(updated.getSalaryRange());
        job.setStatus(updated.getStatus());
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
