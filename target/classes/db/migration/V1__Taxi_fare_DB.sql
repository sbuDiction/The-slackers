--createdb taxi_fare;
--
--psql taxi_fare;

CREATE TABLE DESTINATIONS
(
    id SERIAL PRIMARY KEY,
    location_name TEXT NOT NULL,
    price decimal(10,2) NOT NULL
);

CREATE TABLE USER_TRANSACTIONS
(
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    amount_paid decimal(10,2) NOT NULL,
    change decimal(10,2) NOT NULL
);

CREATE TABLE DRIVER_TRACKER
(
    id SERIAL PRIMARY KEY,
    current_day TEXT NOT NULL,
    money_in decimal(10,2) NOT NULL,
    set_target INT NOT NULL,
    target_measure decimal(10,2) NOT NULL
);


