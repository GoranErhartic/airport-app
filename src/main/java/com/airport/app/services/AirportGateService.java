package com.airport.app.services;

import com.airport.app.api.request.EditGateScheduleRequest;
import com.airport.app.api.request.GateAssignRequest;
import com.airport.app.api.response.GateResponse;
import com.airport.app.exceptions.FlightAlreadyAssignedException;
import com.airport.app.exceptions.FlightNotFoundException;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.exceptions.NoAvailableGatesException;

import java.util.List;
import java.util.UUID;

public interface AirportGateService {

    List<GateResponse> getAllGates();

    GateResponse assignGate(GateAssignRequest request)
            throws NoAvailableGatesException, FlightNotFoundException, FlightAlreadyAssignedException;

    void clearGate(UUID id) throws GateNotFoundException;

    void editGateSchedule(UUID id, EditGateScheduleRequest request) throws GateNotFoundException;
}
