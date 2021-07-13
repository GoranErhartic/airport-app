package com.airport.app.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditGateScheduleRequest {

    private LocalTime from;
    private LocalTime until;

    @JsonCreator
    public EditGateScheduleRequest(LocalTime from, LocalTime until) {
        this.from = from;
        this.until = until;
    }
}
