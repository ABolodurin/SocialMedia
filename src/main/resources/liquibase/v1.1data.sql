--liquibase formatted sql
--changeset ABolodurin:V1.1

INSERT INTO users (username, email, password, role)
VALUES ('username1', 'username1@mail.com', '$2a$10$QOPvlVMioC6tr42qfPHjU.AoT9Zd8DMcD1thD8EI9WZmVE5ppSuLe', 'USER'),
       ('username2', 'username2@mail.com', '$2a$10$F770t500zFCaIqse1HmXju6ZlnqygwTzC/4t3h1g1VH7R8IOh82yy', 'USER'),
       ('username3', 'username3@mail.com', '$2a$10$NwnNGviJbFSZvJ/Z98dTgufa.QgXQBVm4KVK2R/FFfJ0o4/993ZAO', 'USER');

INSERT INTO subscriptions (subscriber, subscription)
VALUES ('username1', 'username2'),
       ('username1', 'username3'),
       ('username3', 'username1'),
       ('username2', 'username1');

INSERT INTO posts (header, content, user_username, timestamp)
VALUES ('hello from username1', 'Hello! My name is username1!', 'username1', '2023-10-16T12:30:25.996121'),
       ('hello from username2', 'Hello! My name is username2!', 'username2', '2023-10-16T12:35:25.996121'),
       ('hello from username3', 'Hello! My name is username3!', 'username3', '2023-10-16T12:40:25.996121'),
       ('Second post from username1', 'This service is great!', 'username1', '2023-10-16T12:45:25.996121'),
       ('Second post from username2', 'This service is great!', 'username2', '2023-10-16T12:50:25.996121'),
       ('Second post from username3', 'This service is great!', 'username3', '2023-10-16T12:55:25.996121');

INSERT INTO messages (producer, consumer, message, timestamp)
VALUES ('username1', 'username2', 'hi, username2', '2023-10-16T12:05:25.996121'),
       ('username2', 'username1', 'hi!', '2023-10-16T12:10:25.996121'),
       ('username1', 'username2', 'Nice to meet you!', '2023-10-16T12:15:25.996121'),
       ('username2', 'username1', 'Nice to meet you too!', '2023-10-16T12:20:25.996121');

-- rollback TRUNCATE TABLE posts;
-- rollback TRUNCATE TABLE messages;
-- rollback TRUNCATE TABLE subscriptions CASCADE;
-- rollback TRUNCATE TABLE users CASCADE;
