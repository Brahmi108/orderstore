alter table ORDERSTORE_ORDER add constraint FK_ORDERSTORE_ORDER_ON_CUSTOMER foreign key (CUSTOMER_ID) references ORDERSTORE_CUSTOMER(ID);
create index IDX_ORDERSTORE_ORDER_ON_CUSTOMER on ORDERSTORE_ORDER (CUSTOMER_ID);
