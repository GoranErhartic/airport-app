package com.airport.app.exceptions;

public class FlightAlreadyAssignedException extends Exception {

    public FlightAlreadyAssignedException(String message) {
        super(message);
    }
}
