package com.emapta.examapi.enums;

import com.emapta.examapi.exception.ComplexValidationException;

import java.util.Arrays;

public enum JobStatus {

    OPEN("open", 1),
    CLOSE("close", 2);

    private String label;
    private Integer value;

    private JobStatus(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static JobStatus getEnum(Integer value) {
        for (JobStatus item : JobStatus.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static JobStatus getEnumByLabel(String label) {
        return Arrays.stream(JobStatus.values()).filter(val -> val.getLabel().equals(label))
                .findAny().orElse(null);

    }

    public static JobStatus isValidRating(String value) {

        switch (value) {
            case "open":
                return JobStatus.OPEN;
            case "close":
                return JobStatus.CLOSE;
            default:
                throw new ComplexValidationException("jobStatus", "isValidRating.invalidStatus");
        }
    }
}
