package com.rest.ets.exceptionalhandler;

import com.rest.ets.exception.InvalidOtpException;
import com.rest.ets.util.AppResponseBuilder;
import com.rest.ets.util.ErrorStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class OtpExceptionHandler {
    private AppResponseBuilder responseBuilder;

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorStructure<String>> handleInvalidOtpException
            (InvalidOtpException exception){
        return responseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(),
                "Invalid OTP or may be expired");
    }
}
