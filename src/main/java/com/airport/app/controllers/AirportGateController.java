package com.airport.app.controllers;

import com.airport.app.api.request.EditGateScheduleRequest;
import com.airport.app.api.request.GateAssignRequest;
import com.airport.app.api.response.GateResponse;
import com.airport.app.exceptions.FlightAlreadyAssignedException;
import com.airport.app.exceptions.FlightNotFoundException;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.exceptions.NoAvailableGatesException;
import com.airport.app.services.AirportGateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gates")
public class AirportGateController {

    private final AirportGateService airportGateService;

    @Autowired
    public AirportGateController(AirportGateService airportGateService) {
        this.airportGateService = airportGateService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GateResponse>> getAllGates() {
        List<GateResponse> response = airportGateService.getAllGates();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(
            path = "/assign",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GateResponse> assignGate(
            @Valid @RequestBody GateAssignRequest request
    ) throws NoAvailableGatesException, FlightNotFoundException, FlightAlreadyAssignedException {
        GateResponse response = airportGateService.assignGate(request);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping(path = "/{id}/make-available")
    public ResponseEntity<Void> clearGate(
            @PathVariable UUID id
    ) throws GateNotFoundException {
        airportGateService.clearGate(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping(
            path = "/{id}/edit-schedule",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editGateSchedule(
            @PathVariable UUID id,
            @RequestBody EditGateScheduleRequest request
    ) throws GateNotFoundException {
        airportGateService.editGateSchedule(id, request);

        return ResponseEntity.ok().build();
    }
}
