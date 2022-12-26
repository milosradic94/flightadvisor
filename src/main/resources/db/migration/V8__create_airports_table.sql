CREATE TABLE airport
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `city_id` BIGINT NOT NULL,
    `iata` VARCHAR(3) NULL,
    `icao` VARCHAR(4) NULL,
    `lat` DECIMAL(10, 7) NULL,
    `long` DECIMAL(10, 7) NULL,
    `altitude` FLOAT NULL,
    `timezone` FLOAT NULL,
    `dst` VARCHAR(1) NULL,
    `tz` VARCHAR(255) NULL,
    `type` VARCHAR(255) NULL,
    `source` VARCHAR(255) NULL,
    CONSTRAINT fk_airport_on_city FOREIGN KEY (`city_id`) REFERENCES city (`id`),
    CONSTRAINT pk_airport PRIMARY KEY (`id`)
);