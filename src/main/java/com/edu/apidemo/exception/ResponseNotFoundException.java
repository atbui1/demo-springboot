package com.edu.apidemo.exception;

public class ResponseNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResponseNotFoundException(String msg) {
        super(msg);
    }
}
