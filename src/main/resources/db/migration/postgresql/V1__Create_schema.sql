CREATE TABLE IF NOT EXISTS tag
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL UNIQUE,
    description      VARCHAR(255),
    price            DECIMAL(8, 2),
    duration         BIGINT,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tag,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate
);

CREATE TABLE IF NOT EXISTS users
(
    id        BIGSERIAL PRIMARY KEY,
    nick_name VARCHAR(64) UNIQUE NOT NULL,
    user_name VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS orders
(
    id             BIGSERIAL PRIMARY KEY,
    price          DECIMAL(6, 2),
    user_id        BIGINT,
    certificate_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id)
);

INSERT INTO tag (name)
VALUES ('relax'),
       ('health'),
       ('extreme'),
       ('animal'),
       ('sport'),
       ('new tag name 1**'),
       ('new tag name 2**'),
       ('new tag name 1'),
       ('new tag name 2');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('massage White lotus', 'one free massage', 11.11, 11, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('Diving club Red Corral', 'one free diving seance', 22.22, 22, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('AirClub Big Plane', 'one free flight', 33.33, 33, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('Club Black Stallion', 'one free horse riding', 44.44, 44, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('Sport Club Gym', 'one free sport class', 55.55, 55, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('test name_UPD', 'test description_UPD', 10.10, 0, '2022-10-06 17:16:08.716647', '2022-10-14 17:55:54.377192');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 5),
       (3, 3),
       (4, 4),
       (4, 5),
       (5, 2),
       (5, 5),
       (6, 7),
       (6, 8);

INSERT INTO users (nick_name, user_name)
VALUES ('user1', 'tom thompson'),
       ('user2', 'tim simpson'),
       ('user3', 'bob brown'),
       ('user4', 'sam sampson'),
       ('user5', 'todd davidson');

INSERT INTO orders (price, user_id, certificate_id)
VALUES (11.11, 1, 1),
       (22.22, 1, 2),
       (33.33, 1, 3),
       (44.44, 1, 4),
       (11.11, 2, 1),
       (22.22, 2, 2),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (44.44, 2, 4),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (33.33, 2, 3),
       (333.333, 2, 3);