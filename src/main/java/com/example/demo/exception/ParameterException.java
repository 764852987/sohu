package com.example.demo.exception;

import lombok.Getter;

@Getter
public class ParameterException extends RuntimeException{
    private int errorCode;

    private String errorMessage;

    public ParameterException(int errorCode) {
        this(errorCode, null);
    }

    public ParameterException(int errorCodeEnum, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
