DROP DATABASE IF EXISTS SocialMedia;
CREATE DATABASE SocialMedia;

CREATE TABLE users
(
    username VARCHAR(63)  NOT NULL,
    email    VARCHAR(63)  NOT NULL,
    password VARCHAR(127) NOT NULL,
    role     VARCHAR(15),
    PRIMARY KEY (username)
);
--
-- CREATE TABLE authorities
-- (
--     id        serial PRIMARY KEY,
--     authority varchar(32)
-- );
--
-- CREATE TABLE users_authorities
-- (
--     id           bigserial PRIMARY KEY,
--     authority_id integer     NOT NULL,
--     username     varchar(50) NOT NULL,
--     CONSTRAINT users_authorities_unq UNIQUE (authority_id, username),
--
--     CONSTRAINT users_authorities_fk_users FOREIGN KEY (username)
--         REFERENCES users (username),
--     CONSTRAINT users_authorities_fk_authorities FOREIGN KEY (authority_id)
--         REFERENCES authorities (id)
-- );
--
-- CREATE TABLE posts
-- (
--     id BIGSERIAL,
--     header VARCHAR(255),
--     content TEXT,
--     creation_timestamp TIMESTAMPTZ,
--     username VARCHAR(63)  NOT NULL,
--     PRIMARY KEY (id),
--
--     CONSTRAINT posts_fk_users FOREIGN KEY (username)
--         REFERENCES users (username)
-- );
--
-- SET timezone = 'Europe/Moscow';
--
-- CREATE TABLE users_subscriptions
-- (
--     id           BIGSERIAL PRIMARY KEY,
--     username     VARCHAR(63)  NOT NULL,
--     subscribed_to VARCHAR(63)  NOT NULL,
--     CONSTRAINT users_subscriptions_unq UNIQUE (username, subscribed_to),
--
--     CONSTRAINT users_subscriptions_fk_users FOREIGN KEY (username)
--         REFERENCES users (username),
--     CONSTRAINT users_subscriptions_fk_subscriptions FOREIGN KEY (subscribed_to)
--         REFERENCES users (username)
-- );