CREATE TABLE comment
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `description` TEXT NOT NULL,
    `city_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    CONSTRAINT fk_comment_on_city FOREIGN KEY (`city_id`) REFERENCES city (`id`),
    CONSTRAINT fk_comment_on_user FOREIGN KEY (`user_id`) REFERENCES user (`id`),
    CONSTRAINT pk_comment PRIMARY KEY (`id`)
);