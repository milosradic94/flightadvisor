CREATE TABLE user
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role_id` BIGINT NOT NULL,
    CONSTRAINT fk_user_on_role FOREIGN KEY (`role_id`) REFERENCES role (`id`),
    CONSTRAINT pk_user PRIMARY KEY (`id`)
);