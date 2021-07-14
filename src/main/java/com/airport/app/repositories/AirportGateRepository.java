package com.airport.app.repositories;

import com.airport.app.models.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AirportGateRepository extends JpaRepository<Gate, UUID> {

    /**
     * Find all available airport gates. That means that there is currently no flight assigned to the gate,
     * and that request is made for appropriate time.
     *
     * @return list of available Gate entities.
     */
    @Query(
            value = "SELECT * FROM GATE WHERE is_available = true AND available_from < :timestamp AND available_until > :timestamp",
            nativeQuery = true
    )
    List<Gate> findAvailableGates(LocalTime timestamp);


    /**
     * Find Gates that have assigned Flight for the given flight id. In case of size > 0 should throw an error as
     * the same flight should not be assignable to multiple gates.
     *
     * @param id - id of flight code
     * @return list of Gate entities that have this flight assigned to them
     */
    List<Gate> findByFlight_id(UUID id);
}
