package com.rest.ets.exceptionalhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rest.ets.exception.BatchNotFoundByIdException;
import com.rest.ets.util.AppResponseBuilder;
import com.rest.ets.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class BatchExceptionHandler {
	private AppResponseBuilder responseBuilder;
	
	@ExceptionHandler(BatchNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handleBatchNotFoundById(BatchNotFoundByIdException exception){
		return responseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "batch not found by given id");
	}

}
