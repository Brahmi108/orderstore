alter table ORDERSTORE_ORDER_PRODUCT add constraint FK_ORDERSTORE_ORDER_PRODUCT_ON_STATUS foreign key (STATUS_ID) references ORDERSTORE_STATUS_ENTITY(ID);
create index IDX_ORDERSTORE_ORDER_PRODUCT_ON_STATUS on ORDERSTORE_ORDER_PRODUCT (STATUS_ID);
