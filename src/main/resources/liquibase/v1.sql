--liquibase formatted sql
--changeset ABolodurin:V1

CREATE TABLE users
(
    username varchar(63) NOT NULL,
    email    varchar     NOT NULL,
    password varchar     NOT NULL,
    role     varchar(31) NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (username),
    CONSTRAINT unq_users_email UNIQUE (email)
);

CREATE TABLE subscriptions
(
    id           bigserial   NOT NULL,
    subscriber   varchar(63) NOT NULL,
    subscription varchar(63) NOT NULL,

    CONSTRAINT pk_subscriptions PRIMARY KEY (id),
    CONSTRAINT unique_sub UNIQUE (subscriber, subscription),
    CONSTRAINT fk_subscriptions_subscriber FOREIGN KEY (subscriber)
        REFERENCES users (username),
    CONSTRAINT fk_subscriptions_subscription FOREIGN KEY (subscription)
        REFERENCES users (username)
);

CREATE TABLE messages
(
    id        bigserial   NOT NULL,
    producer  varchar(63) NOT NULL,
    consumer  varchar(63) NOT NULL,
    message   text        NOT NULL,
    timestamp timestamp   NOT NULL DEFAULT now(),

    CONSTRAINT pk_messages PRIMARY KEY (id),
    CONSTRAINT fk_messages_producer FOREIGN KEY (producer)
        REFERENCES users (username),
    CONSTRAINT fk_messages_consumer FOREIGN KEY (consumer)
        REFERENCES users (username)
);

CREATE TABLE posts
(
    id            bigserial   NOT NULL,
    header        varchar     NOT NULL,
    content       text        NOT NULL,
    timestamp     timestamp   NOT NULL DEFAULT now(),
    user_username varchar(63) NOT NULL,

    CONSTRAINT pk_posts PRIMARY KEY (id),
    CONSTRAINT fk_posts_user FOREIGN KEY (user_username)
        REFERENCES users (username)
);

-- rollback DROP TABLE posts;
-- rollback DROP TABLE messages;
-- rollback DROP TABLE subscriptions;
-- rollback DROP TABLE users;