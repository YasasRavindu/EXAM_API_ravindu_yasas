package com.emapta.examapi.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Candidate
 * Created by  Raveen : 2021/09/15 - Yasas Ravindu
 */

@Data
@Entity
@Table(catalog = "examapi_db", name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String contactNo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "candidate")
    private List<CandidateRegistry> registries;
}
