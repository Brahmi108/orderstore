alter table ORDERSTORE_ADDRESS add constraint FK_ORDERSTORE_ADDRESS_ON_ADDRESS_TYPE foreign key (ADDRESS_TYPE) references ORDERSTORE_ADDRESS_TYPES(ID);
create index IDX_ORDERSTORE_ADDRESS_ON_ADDRESS_TYPE on ORDERSTORE_ADDRESS (ADDRESS_TYPE);
