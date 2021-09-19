package com.emapta.examapi.controller;


import com.emapta.examapi.dto.CandidateRegistryDto;
import com.emapta.examapi.dto.wrapper.ListResponseWrapper;
import com.emapta.examapi.dto.wrapper.SimpleResponseWrapper;
import com.emapta.examapi.entity.CandidateRegistry;
import com.emapta.examapi.entity.Job;
import com.emapta.examapi.enums.JobStatus;
import com.emapta.examapi.modelmapper.ModelMapper;
import com.emapta.examapi.service.JobService;
import com.emapta.examapi.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/registry/v1")
public class RegistryController {

    private RegistryService registryService;
    private JobService jobService;
    private ModelMapper modelMapper;


    @Autowired
    public RegistryController(RegistryService registryService,
                              ModelMapper modelMapper,
                              JobService jobService) {
        this.registryService = registryService;
        this.modelMapper = modelMapper;
        this.jobService = jobService;
    }


    @PostMapping("/")
    public ResponseEntity<SimpleResponseWrapper<CandidateRegistryDto>> create(@Valid @RequestBody CandidateRegistryDto in) {
        CandidateRegistry candidateRegistry = this.modelMapper.map(in, CandidateRegistry.class);

        // Validate the job applying - by checking status whether open or close
        Job job = this.jobService.view(candidateRegistry.getJob().getId());

        // Validate the job applying - by checking status whether open or close
        this.registryService.validateRegistry(job);

        candidateRegistry.setAppliedAt(LocalDateTime.now());
        CandidateRegistry created = this.registryService.create(candidateRegistry);

        List<CandidateRegistry> registryList = this.registryService.getRegistriesByJob(job);

        // If no of positions for a job is equal to job registries , vacancy should be close
        if (registryList.size() == job.getOpenPositions()) {
            job.setStatus(JobStatus.CLOSE);
            this.jobService.create(job);
        }

        CandidateRegistryDto response = this.modelMapper.map(created, CandidateRegistryDto.class);
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<ListResponseWrapper<CandidateRegistryDto>> getByJobId(@PathVariable Integer jobId) {

        Job job = new Job();
        job.setId(jobId);
        List<CandidateRegistry> registries = this.registryService.getRegistriesByJob(job);
        List<CandidateRegistryDto> response = this.modelMapper.map(registries, CandidateRegistryDto.class);
        return new ResponseEntity<>(new ListResponseWrapper<>(response), HttpStatus.OK);
    }

}
