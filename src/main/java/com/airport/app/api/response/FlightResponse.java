package com.airport.app.api.response;

import com.airport.app.models.Flight;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class FlightResponse {

    private String flightCode;
    private String destination;
    private String origin;

    public static FlightResponse toResponse(Flight flight) {
        FlightResponse flightResponse = new FlightResponse();

        if (Objects.nonNull(flight)) {
            flightResponse.setFlightCode(flight.getFlightCode());
            flightResponse.setDestination(flight.getDestination());
            flightResponse.setOrigin(flight.getOrigin());
        }

        return flightResponse;
    }
}
