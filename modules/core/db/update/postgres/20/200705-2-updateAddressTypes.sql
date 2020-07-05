alter table ORDERSTORE_ADDRESS_TYPES rename column owner_id to owner_id__u69906 ;
alter table ORDERSTORE_ADDRESS_TYPES alter column owner_id__u69906 drop not null ;
alter table ORDERSTORE_ADDRESS_TYPES drop constraint FK_ORDERSTORE_ADDRESS_TYPES_ON_OWNER ;
drop index IDX_ORDERSTORE_ADDRESS_TYPES_ON_OWNER ;
