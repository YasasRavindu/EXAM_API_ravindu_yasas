package com.emapta.examapi.entity;


import com.emapta.examapi.enums.JobStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Job
 * Created by  Raveen : 2021/09/15 - Yasas Ravindu
 */

@Getter
@Setter
@Entity
@Table(catalog = "examapi_db", name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String jobName;

    private JobStatus status;

    private int openPositions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "job", orphanRemoval = true)
    private List<CandidateRegistry> registries;





}
