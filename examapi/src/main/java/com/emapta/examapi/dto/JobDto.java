package com.emapta.examapi.dto;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class JobDto {

    private Integer id;

    @NotNull(message = "jobName.missing")
    private String jobName;

    @NotNull(message = "status.missing")
    private String status;

    @NotNull(message = "openPositions.missing")
    @Min(value = 1)
    private int openPositions;


}
