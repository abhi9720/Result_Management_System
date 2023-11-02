package com.nagarro.ProductSearchApi.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 Global exception handler for handling FileSizeLimitExceededException.
	 Provides response for handling file size limit exceeded errors.
	*/
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<?> handleFileSizeLimitExceeded(FileSizeLimitExceededException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("FieldName", ex.getFieldName());
        errorResponse.put("FileName", ex.getFileName());
        errorResponse.put("Message", ex.getMessage());
        errorResponse.put("ActualSize", ex.getActualSize());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
