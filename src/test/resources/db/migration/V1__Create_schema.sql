create table if not exists gift_certificate
(
    id               bigint generated by default as identity,
    name             varchar(255) not null UNIQUE,
    description      varchar(255),
    price            decimal(8,2),
    duration         bigint,
    create_date      timestamp,
    last_update_date timestamp,
    primary key (id)
);

create table if not exists gift_certificate_tag
(
    gift_certificate_id bigint not null,
    tag_id              bigint not null
);

create table if not exists tag
(
    id   bigint generated by default as identity,
    name varchar(255) not null unique,
    primary key (id)
);

alter table gift_certificate_tag
    add constraint tag_id_fk foreign key (tag_id) references tag;

alter table gift_certificate_tag
    add constraint gift_certificate_id_fk foreign key (gift_certificate_id) references gift_certificate;

insert into tag (name)
values ('relax_test'),
       ('health_test'),
       ('extreme_test'),
       ('animal_test'),
       ('sport_test');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('massage White lotus_test', 'one free massage', 11.11, 11, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('Diving club Red Corral_test', 'one free diving seance', 22.22, 22, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('AirClub Big Plane_test', 'one free flight', 33.33, 33, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('Club Black Stallion_test', 'one free horse riding', 44.44, 44, '2022-09-27 11:11:00', '2022-09-27 11:11:00'),
       ('Sport Club Gym_test', 'one free sport class', 55.55, 55, '2022-09-27 11:11:00', '2022-09-27 11:11:00');

insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 5),
       (3, 3),
       (4, 4),
       (4, 5),
       (5, 2),
       (5, 5);
