package com.airport.app.api.request;

import com.airport.app.validators.FlightCodeConstraint;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class GateAssignRequest {

    @FlightCodeConstraint
    private String flightCode;

    private LocalTime timestamp;

    @JsonCreator
    public GateAssignRequest(String flightCode) {
        this.flightCode = flightCode;
        this.timestamp = LocalTime.now();
    }
}
