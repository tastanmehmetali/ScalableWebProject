package com.waes.assignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>
 * to hadle for given / thrown exception(s)
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@ControllerAdvice
public class ExceptionHandleController {

    /**
     * <p>
     * To handle all the IllegalArgumentException that can be thrown by the application.
     * </p>
     *
     * @param exception the resulting exception of the operation that lead to the error
     * @return ResponseEntity the REST response with message for the client regarding invalid payload.
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> invalidArgumentException(final IllegalArgumentException exception) {
        return new ResponseEntity<>("Thrown the exception becaouseof invalid payload.", HttpStatus.BAD_REQUEST);
    }
}
