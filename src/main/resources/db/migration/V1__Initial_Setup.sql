create table customer(
    id bigserial primary key ,
    name text not null,
    email text not null,
    age integer not null
)