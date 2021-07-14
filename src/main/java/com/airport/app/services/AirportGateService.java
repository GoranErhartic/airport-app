package com.airport.app.services;

import com.airport.app.api.request.EditGateScheduleRequest;
import com.airport.app.api.request.GateAssignRequest;
import com.airport.app.api.response.GateResponse;
import com.airport.app.exceptions.FlightNotFoundException;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.exceptions.NoAvailableGatesException;
import com.airport.app.models.Flight;
import com.airport.app.models.Gate;
import com.airport.app.repositories.AirportGateRepository;
import com.airport.app.repositories.FlightRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AirportGateService {

    private AirportGateRepository airportGateRepository;
    private FlightRepository flightRepository;

    @Autowired
    public AirportGateService(
            AirportGateRepository airportGateRepository,
            FlightRepository flightRepository
    ) {
        this.airportGateRepository = airportGateRepository;
        this.flightRepository = flightRepository;
    }

    /**
     * Retrieves all gates that are currently in the system.
     *
     * @return List of GateResponse objects
     */
    public List<GateResponse> getAllGates() {
        List<Gate> gates = airportGateRepository.findAll();
        List<GateResponse> responseList = new ArrayList<>();

        gates.forEach(gate -> {
            GateResponse response = new GateResponse(gate);
            responseList.add(response);
        });

        return responseList;
    }

    /**
     * Assigns first available Gate to the Flight from the list of all available gates when the request comes in.
     *
     * @param request contains flight code that represents the Flight and the timestamp of the request.
     * @return GateResponse that is assigned to the Flight
     * @throws NoAvailableGatesException - if there are no available gates at the time of request
     * @throws FlightNotFoundException   - flight not found for given flight code
     */
    @Transactional
    public GateResponse assignGate(GateAssignRequest request)
            throws NoAvailableGatesException, FlightNotFoundException {
        List<Gate> availableGates = airportGateRepository.findAvailableGates(request.getTimestamp());

        if (CollectionUtils.isEmpty(availableGates)) {
            log.info("There are no available gates at the moment of the request [{}]", request.getTimestamp());
            throw new NoAvailableGatesException("There are no available gates at the moment.");
        }

        Flight flight = flightRepository.getByFlightCode(request.getFlightCode());
        if (Objects.isNull(flight)) {
            log.error("Flight not found for code: {}", request.getFlightCode());
            throw new FlightNotFoundException(String.format("Flight not found for code: %s", request.getFlightCode()));
        }

        Gate gate = availableGates.get(0);
        gate.assignFlight(flight);
        airportGateRepository.save(gate);

        return new GateResponse(gate);
    }

    /**
     * Makes the gate available again.
     *
     * @param id represents the id of the Gate
     * @throws GateNotFoundException - if the gate is not found for the given id
     */
    public void clearGate(UUID id) throws GateNotFoundException {
        Gate gate = getGate(id);
        gate.clearGate();

        airportGateRepository.save(gate);
    }

    /**
     * Edits work hours of a specific gate.
     *
     * @param id      represents the id of the Gate
     * @param request sets the work hours FROM and UNTIL for the Gate
     * @throws GateNotFoundException - if the gate is not found for the given id
     */
    public void editGateSchedule(UUID id, EditGateScheduleRequest request) throws GateNotFoundException {
        Gate gate = getGate(id);
        gate.setAvailableFrom(request.getFrom());
        gate.setAvailableUntil(request.getUntil());

        airportGateRepository.save(gate);
    }

    private Gate getGate(UUID id) throws GateNotFoundException {
        Optional<Gate> optionalGate = airportGateRepository.findById(id);
        return optionalGate.orElseThrow(() ->
                new GateNotFoundException(String.format("Gate not found with ID: %s", id))
        );
    }
}
