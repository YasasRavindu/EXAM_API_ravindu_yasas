package com.emapta.examapi.controller;


import com.emapta.examapi.dto.CandidateDto;
import com.emapta.examapi.dto.wrapper.ListResponseWrapper;
import com.emapta.examapi.dto.wrapper.SimpleResponseWrapper;
import com.emapta.examapi.entity.Candidate;
import com.emapta.examapi.exception.ComplexValidationException;
import com.emapta.examapi.modelmapper.ModelMapper;
import com.emapta.examapi.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/candidate/v1")
public class CandidateController {

    private CandidateService candidateService;
    private ModelMapper modelMapper;

    @Autowired
    CandidateController(CandidateService candidateService, ModelMapper modelMapper) {
        this.candidateService = candidateService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/")
    public ResponseEntity<SimpleResponseWrapper<CandidateDto>> create(@Valid @RequestBody CandidateDto in) {
        Candidate newCandidate = this.modelMapper.map(in, Candidate.class);
        Candidate created = this.candidateService.create(newCandidate);
        CandidateDto response = this.modelMapper.map(created, CandidateDto.class);
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ListResponseWrapper<CandidateDto>> getAll() {
        List<CandidateDto> results = this.modelMapper.map(this.candidateService.getAll(), CandidateDto.class);
        return new ResponseEntity<>(new ListResponseWrapper<>(results), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleResponseWrapper<CandidateDto>> getById(@PathVariable String id) {
        Integer candidateId = getNumericVal(id);
        CandidateDto response = this.modelMapper.map(this.candidateService.view(candidateId), CandidateDto.class);
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponseWrapper<CandidateDto>> update(@Valid @RequestBody CandidateDto in, @PathVariable String id) {

        Integer candidateId = getNumericVal(id);
        Candidate existingCandidate = this.candidateService.view(candidateId);

        Candidate candidate = this.modelMapper.map(in, Candidate.class);
        candidate.setId(existingCandidate.getId());

        Candidate updated = this.candidateService.create(candidate);
        CandidateDto response = this.modelMapper.map(updated, CandidateDto.class);
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponseWrapper<String>> delete(@PathVariable String id) {
        Integer candidateId = getNumericVal(id);
        this.candidateService.delete(candidateId);
        return new ResponseEntity<SimpleResponseWrapper<String>>(
                new SimpleResponseWrapper<String>("deleted id = " + id), HttpStatus.OK);
    }


    private static Integer getNumericVal(String strNum) {
        if (strNum == null) {
            throw new ComplexValidationException("candidate", "Id is null");
        }
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            throw new ComplexValidationException("candidate", "invalid format for id");
        }
    }


}
