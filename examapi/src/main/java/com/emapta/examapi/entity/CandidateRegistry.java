package com.emapta.examapi.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * CandidateRegistry
 * Created by  Raveen : 2021/09/15 - Yasas Ravindu
 */

@Getter
@Setter
@Entity
@Table(catalog = "examapi_db", name = "candidate_registry")
public class CandidateRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private LocalDateTime appliedAt;


}
