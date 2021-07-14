DROP TABLE IF EXISTS gate;

DROP TABLE IF EXISTS flight;

CREATE TABLE flight (
    id UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
    flight_code VARCHAR(128) NOT NULL,
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL
);

CREATE TABLE gate (
    id UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
    is_available BOOLEAN NOT NULL,
    name VARCHAR(128) NOT NULL,
    available_from time,
    available_until time,
    flight_id UUID,
    version BIGINT DEFAULT 0,
    foreign key (flight_id) references flight(id)
);