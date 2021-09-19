package com.emapta.examapi.converters;

import com.emapta.examapi.enums.JobStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * IssueTypeConverter
 * Created by  Yasas Ravindu : 2021/09/14
 */

@Converter(autoApply = true)
public class JobStatusConverter implements AttributeConverter<JobStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(JobStatus jobStatus) {
        return jobStatus.getValue();
    }

    @Override
    public JobStatus convertToEntityAttribute(Integer integer) {
        return JobStatus.getEnum(integer);
    }
}
