package com.airport.app.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = "name", name = "NAME_UNIQUE_CONSTRAINT"
))
public class Gate {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    private String name;
    private boolean isAvailable;
    private LocalTime availableFrom;
    private LocalTime availableUntil;

    @Version
    private Long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public void clearGate() {
        this.setAvailable(true);
        this.setFlight(null);
    }

    public void assignFlight(Flight flight) {
        this.setAvailable(false);
        this.setFlight(flight);
    }
}
