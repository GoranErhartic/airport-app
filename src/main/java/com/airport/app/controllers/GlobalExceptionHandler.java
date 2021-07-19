package com.airport.app.controllers;

import com.airport.app.api.CustomErrorResponse;
import com.airport.app.exceptions.FlightAlreadyAssignedException;
import com.airport.app.exceptions.FlightNotFoundException;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.exceptions.NoAvailableGatesException;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            NoAvailableGatesException.class,
            GateNotFoundException.class,
            FlightNotFoundException.class,
            FlightAlreadyAssignedException.class
    })
    public ResponseEntity<CustomErrorResponse> handleInvalidEntryException(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse(e.getMessage());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomValidationExceptions(
            MethodArgumentNotValidException ex) {
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });

        CustomErrorResponse error = new CustomErrorResponse(errors.stream().findFirst().get());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            StaleObjectStateException.class
    })
    public ResponseEntity<CustomErrorResponse> handleConcurrencyException(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse("Error processing your request. Please try again.");
        error.setStatus((HttpStatus.CONFLICT.value()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
