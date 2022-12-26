CREATE TABLE route
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `airline` VARCHAR(3) NOT NULL,
    `airline_id` BIGINT NOT NULL,
    `source_airport_id` BIGINT NOT NULL,
    `destination_airport_id` BIGINT NOT NULL,
    `codeshare` VARCHAR(1) NULL,
    `stops` BIGINT NULL,
    `equipment` VARCHAR(255) NULL,
    `price` DOUBLE NOT NULL,
    CONSTRAINT fk_route_on_airport_source FOREIGN KEY (`source_airport_id`) REFERENCES airport (`id`),
    CONSTRAINT fk_route_on_airport_destination FOREIGN KEY (`destination_airport_id`) REFERENCES airport (`id`),
    CONSTRAINT pk_route PRIMARY KEY (`id`)
);