package com.emapta.examapi.service;


import com.emapta.examapi.entity.Candidate;
import com.emapta.examapi.entity.CandidateRegistry;
import com.emapta.examapi.entity.QCandidateRegistry;
import com.emapta.examapi.exception.ComplexValidationException;
import com.emapta.examapi.repository.CandidateRegistryRepo;
import com.emapta.examapi.repository.CandidateRepo;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private CandidateRepo candidateRepo;
    private CandidateRegistryRepo candidateRegistryRepo;

    @Autowired
    public CandidateService(CandidateRepo repo, CandidateRegistryRepo candidateRegistryRepo) {
        this.candidateRepo = repo;
        this.candidateRegistryRepo = candidateRegistryRepo;
    }


    public List<Candidate> getAll() {
        return this.candidateRepo.findAll();
    }

    public Candidate view(Integer id) {
        Optional<Candidate> opt = this.candidateRepo.findById(id);
        if (!opt.isPresent()) {
            throw new ComplexValidationException("candidate", "candidateNotExist");
        }
        return opt.get();
    }


    public Candidate create(Candidate newCandidate) {
        try {
            return this.candidateRepo.save(newCandidate);
        } catch (DataIntegrityViolationException se) {
            se.printStackTrace();
            throw new ComplexValidationException("candidate", "duplicate value for " + newCandidate.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("candidate", "persistIssue");
        }
    }

    public void delete(Integer id) {
        Candidate candidate = view(id);
        BooleanBuilder builder = new BooleanBuilder(QCandidateRegistry.candidateRegistry.candidate.eq(candidate));
        List<CandidateRegistry> registries = (List<CandidateRegistry>) candidateRegistryRepo.findAll(builder);

        if (registries.size() > 0) {
            throw new ComplexValidationException("delete", "Candidate is having job registries");
        }
        this.candidateRepo.delete(candidate);
    }


}
