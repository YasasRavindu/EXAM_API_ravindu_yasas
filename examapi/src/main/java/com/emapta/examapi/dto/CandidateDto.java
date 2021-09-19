package com.emapta.examapi.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Data
public class CandidateDto {

    private Integer id;

    @NotNull(message = "firstName.empty")
    private String firstName;

    @NotNull(message = "lastName.empty")
    private String lastName;

    @NotNull(message = "email.missing")
    @Email
    private String email;

    @NotEmpty(message = "Mandatory field Contact No is empty")
    @Pattern(regexp = "^\\d{10}$")
    private String contactNo;
}
