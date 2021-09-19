package com.emapta.examapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CandidateRegistryDto {

    private Integer id;
    private CandidateData candidate;
    private JobData job;
    private LocalDateTime appliedAt;


    @Data
    public static class CandidateData {
        @NotNull(message = "missing.candidateId")
        private Integer id;
        private String firstName;
        private String lastName;
    }

    @Data
    public static class JobData {
        @NotNull(message = "missing.jobId")
        private Integer id;
        private String jobName;
    }

}
