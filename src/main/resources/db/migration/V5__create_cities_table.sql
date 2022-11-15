CREATE TABLE city
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    CONSTRAINT pk_city PRIMARY KEY (`id`)
);