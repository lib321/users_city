create table city
(
    id    serial8,
    index varchar not null,
    name  varchar not null
);

create table users
(
    id       serial8,
    city_id  int8    not null,
    login    varchar not null,
    password varchar not null,
    name     varchar not null,
    surname  varchar not null
);



