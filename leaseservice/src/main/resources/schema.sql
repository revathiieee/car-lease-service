create schema if not exists authdb;
use authdb;
create table if not exists users
(
    id          int auto_increment primary key,
    name   varchar(30),
    email       varchar(30),
    password    varchar(255)
);