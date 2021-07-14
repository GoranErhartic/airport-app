package com.airport.app.api.request;

import com.airport.app.validators.FlightCodeConstraint;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class GateAssignRequest {

    @FlightCodeConstraint
    @Schema(description = "Flight code format must be three letters, dash, four numbers",
            example = "POR-6623")
    private String flightCode;
    @JsonIgnore
    private LocalTime timestamp;

    @JsonCreator
    public GateAssignRequest(String flightCode) {
        this.flightCode = flightCode;
        this.timestamp = LocalTime.now();
    }
}
