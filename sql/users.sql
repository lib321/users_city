create table city
(
    id    serial8,
    index varchar not null,
    name  varchar not null,
    primary key (id)
);

create table users
(
    id       serial8,
    city_id  int8    not null,
    login    varchar not null,
    password varchar not null,
    name     varchar not null,
    surname  varchar not null,
    primary key (id),
    foreign key (city_id) references city (id)
);

insert into city (index, name)
values ('0000010', 'Astana'),
       ('0000020', 'Alma-ata'),
       ('0000400', 'Moscow'),
       ('0000050', 'London');



