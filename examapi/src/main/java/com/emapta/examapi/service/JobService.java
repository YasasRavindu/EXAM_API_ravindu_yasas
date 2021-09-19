package com.emapta.examapi.service;

import com.emapta.examapi.entity.Job;
import com.emapta.examapi.enums.JobStatus;
import com.emapta.examapi.exception.ComplexValidationException;
import com.emapta.examapi.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private JobRepo jobRepo;

    @Autowired
    public JobService(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    public Job create(Job newJob) {
        try {
            return this.jobRepo.save(newJob);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("job", "persistIssue");
        }
    }

    public List<Job> getAll() {
        return this.jobRepo.findAll();
    }

    public Job view(Integer id) {
        Optional<Job> opt = this.jobRepo.findById(id);
        if (!opt.isPresent()) {
            throw new ComplexValidationException("job", "jobNotExist");
        }
        return opt.get();
    }

    public Job changeStatus(Job job, JobStatus status) {
        job.setStatus(status);
        return this.jobRepo.save(job);
    }


}
