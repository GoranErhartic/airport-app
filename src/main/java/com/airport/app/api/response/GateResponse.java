package com.airport.app.api.response;

import com.airport.app.models.Flight;
import com.airport.app.models.Gate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class GateResponse {

    private UUID id;
    private String gateName;
    private Flight flight;

    public GateResponse(Gate gate) {
        this.id = gate.getId();
        this.gateName = gate.getName();
        this.flight = gate.getFlight();
    }
}
