package com.emapta.examapi.component;


import com.emapta.examapi.dto.ValidationFailure;
import com.emapta.examapi.dto.wrapper.BaseResponseWrapper;
import com.emapta.examapi.dto.wrapper.ValidationFailureResponseWrapper;
import com.emapta.examapi.enums.RestApiResponseStatus;
import com.emapta.examapi.exception.ComplexValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseWrapper> handleExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new BaseResponseWrapper(RestApiResponseStatus.ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ComplexValidationException.class)
    public ResponseEntity<BaseResponseWrapper> handleComplexValidationException(ComplexValidationException ex,
                                                                                WebRequest request) {
        if (ex.getValidationFailures() != null) {
            return new ResponseEntity<>(
                    new ValidationFailureResponseWrapper(ex.getValidationFailures()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(
                    new ValidationFailureResponseWrapper(ex.getField(), ex.getCode()), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult results = ex.getBindingResult();

        List<ValidationFailure> validationErrors = results.getFieldErrors().stream()
                .map(item -> new ValidationFailure(item.getField(), item.getDefaultMessage()))
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ValidationFailureResponseWrapper(validationErrors), headers, status,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        BindingResult results = ex.getBindingResult();

        List<ValidationFailure> validationErrors = results.getFieldErrors().stream()
                .map(item -> new ValidationFailure(item.getField(), item.getDefaultMessage()))
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ValidationFailureResponseWrapper(validationErrors), headers, status,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex,
                new ValidationFailureResponseWrapper("requestBody", "requestBody.unreadable/requiredContentMissing"),
                headers, status, request);
    }


}
