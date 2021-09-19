package com.emapta.examapi.repository;

import com.emapta.examapi.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer>, QuerydslPredicateExecutor<Candidate> {
}
