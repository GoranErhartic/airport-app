package com.airport.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = "flightCode", name = "FLIGHT_CODE_UNIQUE_CONSTRAINT"
))
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Flight {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    private String flightCode;
    private String destination;
    private String origin;
}
