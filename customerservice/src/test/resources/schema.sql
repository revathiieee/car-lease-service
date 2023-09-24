create schema if not exists cusdb;
use cusdb;
create table if not exists customer
(
    id          int auto_increment primary key,
    name   varchar(30),
    street      varchar(30),
    house_number varchar(30),
    postal_code varchar(30),
    city        varchar(30),
    email       varchar(30),
    phone_number varchar(30)
);