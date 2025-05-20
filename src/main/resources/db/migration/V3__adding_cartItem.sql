ALTER TABLE cart
    ADD CONSTRAINT PRIMARY KEY (id);

create table cartItem
(
    id        bigint auto_increment
        primary key,
    cart      binary(16)  not null,
    productID BIGINT      not null,
    quantity  int default 1 not null,
    constraint cartItem_cart_id_fk
        foreign key (cart) references cart (id) on delete cascade,
    constraint cartItem_products_id_fk
        foreign key (productID) references products (id) on delete cascade
);
alter table cartitem
    add constraint cartitem_cart_product_unique
        unique (cart, productID);
