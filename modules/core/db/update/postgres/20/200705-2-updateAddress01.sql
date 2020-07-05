alter table ORDERSTORE_ADDRESS add constraint FK_ORDERSTORE_ADDRESS_ON_CUSTOMER foreign key (CUSTOMER_ID) references ORDERSTORE_CUSTOMER(ID) on delete CASCADE;
create index IDX_ORDERSTORE_ADDRESS_ON_CUSTOMER on ORDERSTORE_ADDRESS (CUSTOMER_ID);
