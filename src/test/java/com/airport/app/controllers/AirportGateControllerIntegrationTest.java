package com.airport.app.controllers;

import com.airport.app.api.request.GateAssignRequest;
import com.airport.app.exceptions.NoAvailableGatesException;
import com.airport.app.models.Gate;
import com.airport.app.repositories.AirportGateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirportGateControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AirportGateRepository airportGateRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

//    @Test
//    public void shouldFailForUnknownUUID() throws Exception {
//        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
//        this.mockMvc.perform(put("/gate/clear-gate")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(generateClearGateRequest(uuid))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(mvcResult -> {
//                    assertTrue(mvcResult.getResolvedException() instanceof GateNotFoundException);
//                });
//    }
//
//    @Test
//    public void shouldSuccessfullyClearGate() throws Exception {
//        List<Gate> gates = airportGateRepository.findAll();
//        Gate gate = gates.stream().filter(item -> !item.isAvailable()).findFirst().get();
//
//        assertNotNull(gate);
//        assertFalse(gate.isAvailable());
//
//        this.mockMvc.perform(put("/gate/clear-gate")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(generateClearGateRequest(gate.getId()))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        gate = airportGateRepository.findById(gate.getId()).get();
//        assertNotNull(gate);
//        assertTrue(gate.isAvailable());
//    }

    @Test
    public void shouldSuccessfullyAssignGate() throws Exception {
        this.mockMvc.perform(post("/gate/assign-gate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateAssignGateRequest("BAG-1234"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailAssignGateForInvalidFlightCodeFormat() throws Exception {
        this.mockMvc.perform(post("/gate/assign-gate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateAssignGateRequest("BAG-BLAB32"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException)
                );
    }

    @Test
    public void shouldFailAssignGateForNoAvailableGates() throws Exception {
        List<Gate> gates = airportGateRepository.findAll();
        gates.forEach(gate -> {
            gate.setAvailable(false);
            airportGateRepository.save(gate);
        });

        this.mockMvc.perform(post("/gate/assign-gate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateAssignGateRequest("BAG-1234"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NoAvailableGatesException)
                );
    }

    private String generateAssignGateRequest(String id) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(new GateAssignRequest(id));
    }
}
