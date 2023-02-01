CREATE TABLE revinfo
(
    `rev` BIGINT AUTO_INCREMENT NOT NULL,
    `revtstmp` BIGINT,
    CONSTRAINT `pk_revinfo` PRIMARY KEY (`rev`)
);

CREATE TABLE comment_aud
(
    `id` BIGINT NOT NULL,
    `rev` INTEGER NOT NULL,
    `revtype` SMALLINT,
    `description` TEXT NOT NULL,
    `created_at` datetime NULL,
    `updated_at` datetime NULL,
    `inappropriate` BOOLEAN DEFAULT false,
    CONSTRAINT pk_comment_audit PRIMARY KEY (`id`, `rev`)
);