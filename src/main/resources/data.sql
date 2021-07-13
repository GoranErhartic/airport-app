insert into GATE (is_available, name, available_from, available_until) values (true, 'GATE 1', '10:00', '16:00');
insert into GATE (is_available, name, available_from, available_until) values (false, 'GATE 2', '10:00', '16:00');
insert into GATE (is_available, name, available_from, available_until) values (true, 'GATE 3', '7:00', '18:00');
insert into GATE (is_available, name, available_from, available_until) values (false, 'GATE 4', '7:00', '11:00');
insert into GATE (is_available, name, available_from, available_until) values  (false, 'GATE 5', '10:00', '16:00');

insert into FLIGHT (flight_code, destination, origin) values ('JAT-3146', 'LUX', 'BEG');
insert into FLIGHT (flight_code, destination, origin) values ('POR-6623', 'LIS', 'BEG');
insert into FLIGHT (flight_code, destination, origin) values ('TIP-8843', 'MAD', 'LUX');
insert into FLIGHT (flight_code, destination, origin) values ('ANK-4576', 'BAR', 'LON');
insert into FLIGHT (flight_code, destination, origin) values ('SSY-6144', 'STO', 'BER');
