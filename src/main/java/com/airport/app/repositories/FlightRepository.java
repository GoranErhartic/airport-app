package com.airport.app.repositories;

import com.airport.app.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    /**
     * Retrieve single entity by flight code.
     *
     * @param code String
     * @return Flight entity
     */
    Flight getByFlightCode(String code);
}
