ALTER TABLE cart
    ADD CONSTRAINT PRIMARY KEY (id);

create table cartItem
(
    id        bigint        not null
        primary key,
    cart      varchar(36) not null,
    productID BIGINT      not null,
    quantity  int         not null,
    constraint cartItem_cart_id_fk
        foreign key (cart) references cart (id),
    constraint cartItem_products_id_fk
        foreign key (productID) references products (id)
);
