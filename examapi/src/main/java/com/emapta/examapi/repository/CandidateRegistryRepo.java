package com.emapta.examapi.repository;

import com.emapta.examapi.entity.Candidate;
import com.emapta.examapi.entity.CandidateRegistry;
import com.emapta.examapi.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRegistryRepo extends JpaRepository<CandidateRegistry, Integer>, QuerydslPredicateExecutor<CandidateRegistry> {

    List<CandidateRegistry> findByJob(Job job);

    Optional<CandidateRegistry> findByCandidateAndJob(Candidate candidate, Job job);

}
