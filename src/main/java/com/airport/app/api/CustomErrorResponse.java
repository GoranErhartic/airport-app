package com.airport.app.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomErrorResponse {

    private String errorMsg;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public CustomErrorResponse(String errorMsg) {
        this.errorMsg = errorMsg;
        this.timestamp = LocalDateTime.now();
    }
}
