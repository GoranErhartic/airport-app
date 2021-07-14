package com.airport.app.services;

import com.airport.app.api.request.EditGateScheduleRequest;
import com.airport.app.api.request.GateAssignRequest;
import com.airport.app.api.response.GateResponse;
import com.airport.app.exceptions.FlightAlreadyAssignedException;
import com.airport.app.exceptions.FlightNotFoundException;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.exceptions.NoAvailableGatesException;
import com.airport.app.models.Flight;
import com.airport.app.models.Gate;
import com.airport.app.repositories.AirportGateRepository;
import com.airport.app.repositories.FlightRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportGateServiceUnitTest {

    @Autowired
    private AirportGateService airportGateService;

    @MockBean
    private AirportGateRepository airportGateRepository;
    @MockBean
    private FlightRepository flightRepository;

    @Test
    public void getAllGatesSuccess() {
        List<Gate> gates = getGates();

        when(airportGateRepository.findAll()).thenReturn(gates);

        List<GateResponse> response = airportGateService.getAllGates();
        assertEquals(3, response.size());
    }

    @Test
    public void assignGateSuccess() throws NoAvailableGatesException, FlightNotFoundException, FlightAlreadyAssignedException {
        List<Gate> gates = getGates();

        Flight flight = new Flight();
        flight.setId(UUID.randomUUID());
        flight.setFlightCode("XXX-1234");

        when(airportGateRepository.findAvailableGates(any(LocalTime.class))).thenReturn(gates);
        when(flightRepository.getByFlightCode(eq("XXX-1234"))).thenReturn(flight);

        GateResponse response = airportGateService.assignGate(new GateAssignRequest("XXX-1234"));
        assertEquals(response.getGateName(), "GATE 1");
        assertEquals(response.getFlight().getFlightCode(), "XXX-1234");
    }

    @Test(expected = NoAvailableGatesException.class)
    public void assignGateFailForNoAvailableGates() throws NoAvailableGatesException, FlightNotFoundException, FlightAlreadyAssignedException {
        when(airportGateRepository.findAvailableGates(any(LocalTime.class))).thenReturn(null);
        airportGateService.assignGate(new GateAssignRequest("XXX-1234"));
    }

    @Test(expected = FlightNotFoundException.class)
    public void assignGateFailForFlightCodeNotFound() throws NoAvailableGatesException, FlightNotFoundException, FlightAlreadyAssignedException {
        List<Gate> gates = getGates();

        when(airportGateRepository.findAvailableGates(any(LocalTime.class))).thenReturn(gates);
        when(flightRepository.getByFlightCode(eq("XXX-1234"))).thenReturn(null);

        airportGateService.assignGate(new GateAssignRequest("XXX-1234"));
    }

    @Test(expected = FlightAlreadyAssignedException.class)
    public void assignGateFailForFlightAlreadyAssigned() throws NoAvailableGatesException, FlightNotFoundException, FlightAlreadyAssignedException {
        List<Gate> gates = getGates();
        Flight flight = new Flight();
        flight.setId(UUID.randomUUID());
        flight.setFlightCode("XXX-1234");

        when(airportGateRepository.findAvailableGates(any(LocalTime.class))).thenReturn(gates);
        when(flightRepository.getByFlightCode(eq("XXX-1234"))).thenReturn(flight);
        when(airportGateRepository.findByFlight_id(any(UUID.class))).thenReturn(gates);

        airportGateService.assignGate(new GateAssignRequest("XXX-1234"));
    }

    @Test(expected = GateNotFoundException.class)
    public void clearGateFailForUnknownGate() throws GateNotFoundException {
        when(airportGateRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        airportGateService.clearGate(UUID.randomUUID());
    }

    @Test
    public void clearGateSuccess() throws GateNotFoundException {
        Flight flight = new Flight();
        flight.setId(UUID.randomUUID());
        flight.setFlightCode("XXX-1234");

        Gate gate = new Gate();
        gate.setId(UUID.randomUUID());
        gate.setName("GATE 1");
        gate.setAvailable(false);
        gate.setFlight(flight);

        when(airportGateRepository.findById(any(UUID.class))).thenReturn(Optional.of(gate));

        assertFalse(gate.isAvailable());
        assertNotNull(gate.getFlight());

        airportGateService.clearGate(UUID.randomUUID());

        assertTrue(gate.isAvailable());
        assertNull(gate.getFlight());
    }

    @Test(expected = GateNotFoundException.class)
    public void editGateScheduleFailForUnknownGate() throws GateNotFoundException {
        when(airportGateRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        airportGateService.editGateSchedule(UUID.randomUUID(), generateEditScheduleRequest());
    }

    @Test
    public void editGateScheduleSuccess() throws GateNotFoundException {
        Gate gate = new Gate();
        gate.setId(UUID.randomUUID());
        gate.setName("GATE 1");
        gate.setAvailable(false);
        gate.setAvailableFrom(LocalTime.of(8, 0));
        gate.setAvailableUntil(LocalTime.of(16, 0));

        when(airportGateRepository.findById(any(UUID.class))).thenReturn(Optional.of(gate));
        airportGateService.editGateSchedule(UUID.randomUUID(), generateEditScheduleRequest());

        assertEquals(gate.getAvailableFrom(), LocalTime.of(3, 30));
        assertEquals(gate.getAvailableUntil(), LocalTime.of(15, 0));
    }

    private List<Gate> getGates() {
        List<Gate> gates = new ArrayList<>();
        Gate one = new Gate();
        one.setId(UUID.randomUUID());
        one.setName("GATE 1");
        Gate two = new Gate();
        Gate three = new Gate();

        gates.add(one);
        gates.add(two);
        gates.add(three);

        return gates;
    }

    private EditGateScheduleRequest generateEditScheduleRequest() {
        return new EditGateScheduleRequest(LocalTime.of(3, 30), LocalTime.of(15, 0));
    }
}
