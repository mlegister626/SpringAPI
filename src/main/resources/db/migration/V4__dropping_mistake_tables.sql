alter table cartItem
    drop foreign key  cartItem_cart_id_fk;

drop table cart;
drop table cartitem;