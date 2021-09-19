package com.emapta.examapi.repository;


import com.emapta.examapi.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job, Integer>, QuerydslPredicateExecutor<Job> {

    @Query(nativeQuery = true, value = "SELECT status FROM job where id=:jobId")
    public String getStatusByJobId(@Param("jobId") Integer jobId);

}
