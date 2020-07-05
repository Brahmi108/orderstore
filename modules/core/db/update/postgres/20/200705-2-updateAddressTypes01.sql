alter table ORDERSTORE_ADDRESS_TYPES add column VERSION integer ^
update ORDERSTORE_ADDRESS_TYPES set VERSION = 0 where VERSION is null ;
alter table ORDERSTORE_ADDRESS_TYPES alter column VERSION set not null ;
