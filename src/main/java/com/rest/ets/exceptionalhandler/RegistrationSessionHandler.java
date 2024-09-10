package com.rest.ets.exceptionalhandler;

import com.rest.ets.exception.RegistrationSessionExpiredException;
import com.rest.ets.util.AppResponseBuilder;
import com.rest.ets.util.ErrorStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class RegistrationSessionHandler {
    private AppResponseBuilder responseBuilder;
    public ResponseEntity<ErrorStructure<String>> handleRegistrationExpiredException
            (RegistrationSessionExpiredException exception){
        return  responseBuilder.error(HttpStatus.NOT_FOUND,exception.getMessage(),
                "session Expired");
    }

}
