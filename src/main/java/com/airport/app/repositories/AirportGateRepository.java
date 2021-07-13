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
}
