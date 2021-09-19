package com.emapta.examapi.service;


import com.emapta.examapi.entity.CandidateRegistry;
import com.emapta.examapi.entity.Job;
import com.emapta.examapi.enums.JobStatus;
import com.emapta.examapi.exception.ComplexValidationException;
import com.emapta.examapi.repository.CandidateRegistryRepo;
import com.emapta.examapi.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistryService {

    private CandidateRegistryRepo candidateRegistryRepo;
    private JobRepo jobRepo;

    @Autowired
    public RegistryService(CandidateRegistryRepo candidateRegistryRepo, JobRepo jobRepo) {
        this.candidateRegistryRepo = candidateRegistryRepo;
        this.jobRepo = jobRepo;
    }


    public CandidateRegistry create(CandidateRegistry candidateRegistry) {


        Optional<CandidateRegistry> existing = this.candidateRegistryRepo.findByCandidateAndJob(candidateRegistry.getCandidate(), candidateRegistry.getJob());
        if (existing.isPresent()) {
            throw new ComplexValidationException("candidateRegistry", "alreadyApplied");
        }

        try {
            return this.candidateRegistryRepo.save(candidateRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("candidateRegistry", "persistIssue");
        }
    }

    public List<CandidateRegistry> getRegistriesByJob(Job job) {
        try {
            return this.candidateRegistryRepo.findByJob(job);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("getRegistriesByJob", "issueInFetch");
        }
    }

    public void validateRegistry(Job job) {
        JobStatus status = job.getStatus();
        if (status == JobStatus.CLOSE) {
            throw new ComplexValidationException("registry", "jobClose");
        }

    }


}
