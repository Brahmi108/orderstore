alter table ORDERSTORE_ORDER_PRODUCT add constraint FK_ORDERSTORE_ORDER_PRODUCT_ON_OWNER foreign key (OWNER_ID) references ORDERSTORE_OWNER(ID);
create index IDX_ORDERSTORE_ORDER_PRODUCT_ON_OWNER on ORDERSTORE_ORDER_PRODUCT (OWNER_ID);
