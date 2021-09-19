package com.emapta.examapi.controller;

import com.emapta.examapi.dto.JobDto;
import com.emapta.examapi.dto.wrapper.ListResponseWrapper;
import com.emapta.examapi.dto.wrapper.SimpleResponseWrapper;
import com.emapta.examapi.entity.Job;
import com.emapta.examapi.enums.JobStatus;
import com.emapta.examapi.exception.ComplexValidationException;
import com.emapta.examapi.modelmapper.ModelMapper;
import com.emapta.examapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/job/v1")
public class JobController {

    private JobService jobService;
    private ModelMapper modelMapper;


    @Autowired
    public JobController(JobService jobService, ModelMapper modelMapper) {
        this.jobService = jobService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/")
    public ResponseEntity<SimpleResponseWrapper<JobDto>> create(@Valid @RequestBody JobDto in) {
        Job newJob = this.modelMapper.map(in, Job.class);
        JobStatus statusEnum = JobStatus.isValidRating(in.getStatus());
        newJob.setStatus(statusEnum);
        Job created = this.jobService.create(newJob);
        JobDto response = this.modelMapper.map(created, JobDto.class);
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<ListResponseWrapper<JobDto>> getAll() {
        List<JobDto> results = this.modelMapper.map(this.jobService.getAll(), JobDto.class);
        return new ResponseEntity<>(new ListResponseWrapper<>(results), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleResponseWrapper<JobDto>> getById(@PathVariable String id) {
        Integer jobId = getNumericVal(id);
        JobDto response = this.modelMapper.map(this.jobService.view(jobId), JobDto.class);
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.OK);
    }

    private static Integer getNumericVal(String strNum) {
        if (strNum == null) {
            throw new ComplexValidationException("job", "Id is null");
        }
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            throw new ComplexValidationException("job", "invalid format for id");
        }
    }

}
