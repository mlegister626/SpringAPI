create table cart
(
    id   binary(16) default (uuid_to_bin(uuid())) not null
        primary key,
    date date       default (current_date())  not null
);

create table cartItems
(
    id         bigint auto_increment
        primary key,
    cart_id    binary(16)    not null,
    product_id bigint        not null,
    quantity   int default 1 not null,
    constraint cartItems_product_cart
        unique (cart_id, product_id),
    constraint cartItems_cart_id_fk
        foreign key (cart_id) references cart (id)
            on delete cascade,
    constraint cartItems_products_id_fk
        foreign key (product_id) references products (id)
            on delete cascade
);
