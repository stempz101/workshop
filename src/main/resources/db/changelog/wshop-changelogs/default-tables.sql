create table users_ (
    id serial primary key,
    username varchar(50) not null unique,
    email varchar(100) not null unique,
    mobile varchar(15) unique
);
