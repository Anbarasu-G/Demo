package com.rest.ets.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationSessionExpiredException extends  RuntimeException{

    private  final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
