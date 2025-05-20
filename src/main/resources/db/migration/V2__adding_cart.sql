create table cart
(
    id          binary(16) default (uuid_to_bin(uuid())) not null,
    dateCreated DATE default (current_date()) not null
);
