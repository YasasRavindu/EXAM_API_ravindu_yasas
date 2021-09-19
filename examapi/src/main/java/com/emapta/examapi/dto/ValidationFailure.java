package com.emapta.examapi.dto;


import lombok.Data;

/**
 * ValidationFailure
 * Created by YASAS RAVINDU : 2021/09/14
 */

@Data
public class ValidationFailure {

    public ValidationFailure(String field, String code) {
        this.field = field;
        this.code = code;
    }


    private String field;

    private String code;


}
