package com.airport.app.controllers;

import com.airport.app.api.request.EditGateScheduleRequest;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.services.AirportGateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AirportGateService airportGateService;

    @Autowired
    public AdminController(AirportGateService airportGateService) {
        this.airportGateService = airportGateService;
    }

    @PutMapping(
            path = "/edit-gate-schedule/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editGateSchedule(
            @PathVariable UUID id,
            @RequestBody EditGateScheduleRequest request
    ) throws GateNotFoundException {
        airportGateService.editGateSchedule(id, request);

        return ResponseEntity.ok().build();
    }
}
