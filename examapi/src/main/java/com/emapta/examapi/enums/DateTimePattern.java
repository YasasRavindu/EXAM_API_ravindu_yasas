package com.emapta.examapi.enums;


public enum DateTimePattern {

    STRICT_DATE("uuuu-MM-dd"), STRICT_TIME("HH:mm"), STRICT_DATE_TIME("uuuu-MM-dd HH:mm:ss");

    private String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
