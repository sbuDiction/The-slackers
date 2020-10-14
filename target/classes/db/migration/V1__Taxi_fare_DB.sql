CREATE TABLE DESTINATIONS
(
    id SERIAL PRIMARY KEY,
    location_name TEXT NOT NULL,
    price decimal(10,2) NOT NULL
);

CREATE TABLE DAYS
(
    id SERIAL PRIMARY KEY,
    days_in_a_week TEXT NOT NULL
);

CREATE TABLE USER_TRANSACTIONS
(
    user_ref INT REFERENCES USER_NAMES (id) ON DELETE CASCADE ON UPDATE CASCADE,
    change decimal(10,2) NOT NULL,
    travel_ref INT REFERENCES DESTINATIONS (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE DRIVER_TRACKER
(
    id SERIAL PRIMARY KEY,
    current_day TEXT NOT NULL,
    money_in decimal(10,2) NOT NULL,
    set_target decimal(10,2) NOT NULL,
    target_measure decimal(10,2) NOT NULL
);

CREATE TABLE USER_NAMES
(
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    amount_paid decimal(10,2) NOT NULL
);

CREATE TABLE HISTORY
(
--    price_ref INT REFERENCES DESTINATIONS (id) ON DELETE CASCADE ON UPDATE CASCADE,
    price decimal(10,2) NOT NULL,
    days_ref INT REFERENCES DAYS (id) ON DELETE CASCADE ON UPDATE CASCADE,
    time_stamp TEXT NOT NULL
);

CREATE TABLE TAXI_PASSENGERS
(
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    destination TEXT NOT NULL,
    passenger_amount DECIMAL(10,2),
    change DECIMAL(10,2)
);



