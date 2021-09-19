package com.emapta.examapi.dto.wrapper;


import com.emapta.examapi.enums.RestApiResponseStatus;


public class SimpleResponseWrapper<T> extends BaseResponseWrapper {

    private T content;

    public SimpleResponseWrapper(T content) {
        super(RestApiResponseStatus.OK);
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
