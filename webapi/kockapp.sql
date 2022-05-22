CREATE DATABASE IF NOT EXISTS Kockapp;

CREATE TABLE Results(
    device_id CHAR(16),
    cube_type INT NOT NULL,
    result LONG NOT NULL,
    PRIMARY KEY(device_id));