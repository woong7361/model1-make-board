package com.study.exception;

/**
 * 자체 처리할 custom Exception
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
