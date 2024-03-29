package com.study.exception;

/**
 * checked exception을 wrapping한 custom Exception class
 */
public class WrapCheckedException extends RuntimeException {

    private Exception checkedException;
    public WrapCheckedException(String message, Exception checkedException) {
        super(message);
        this.checkedException = checkedException;

    }

    public void printStackTrace() {
        checkedException.printStackTrace();
    }

}
