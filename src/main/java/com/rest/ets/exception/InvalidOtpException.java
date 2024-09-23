package com.rest.ets.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidOtpException extends RuntimeException{
    private String message;

    @Override
    public final String getMessage() {
        return message;
    }
}
