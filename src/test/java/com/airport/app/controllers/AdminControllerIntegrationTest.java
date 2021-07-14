package com.airport.app.controllers;

import com.airport.app.api.request.EditGateScheduleRequest;
import com.airport.app.exceptions.GateNotFoundException;
import com.airport.app.models.Gate;
import com.airport.app.repositories.AirportGateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final static UUID UNKNOWN_GATE_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AirportGateRepository airportGateRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.objectMapper.findAndRegisterModules();
    }

    @Test
    public void shouldSuccessfullyEditGateSchedule() throws Exception {
        Gate gate = getGate();

        this.mockMvc.perform(put("/admin/edit-gate-schedule/{id}", gate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateEditScheduleRequest())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        gate = airportGateRepository.findById(gate.getId()).get();
        assertNotNull(gate);
        assertEquals(gate.getAvailableFrom(), LocalTime.of(5, 30));
        assertEquals(gate.getAvailableUntil(), LocalTime.of(12, 45));
    }

    @Test
    public void shouldFailEditGateScheduleForUnknownGate() throws Exception {
        this.mockMvc.perform(put("/admin/edit-gate-schedule/{id}", UNKNOWN_GATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateEditScheduleRequest())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> {
                    assertTrue(mvcResult.getResolvedException() instanceof GateNotFoundException);
                });
    }

    private Gate getGate() {
        List<Gate> gates = airportGateRepository.findAll();
        Gate gate = gates.stream().filter(item -> !item.isAvailable()).findFirst().get();

        assertNotNull(gate);
        assertFalse(gate.isAvailable());

        return gate;
    }

    private String generateEditScheduleRequest() throws JsonProcessingException {
        EditGateScheduleRequest request = new EditGateScheduleRequest(
                LocalTime.parse("05:30"),
                LocalTime.parse("12:45")
        );

        return objectMapper.writeValueAsString(request);
    }
}
