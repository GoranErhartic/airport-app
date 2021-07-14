package com.airport.app.api.response;

import com.airport.app.models.Gate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GateResponse {

    private UUID id;
    private String gateName;
    private FlightResponse flight;

    public GateResponse(Gate gate) {
        this.id = gate.getId();
        this.gateName = gate.getName();
        this.flight = FlightResponse.toResponse(gate.getFlight());
    }
}
