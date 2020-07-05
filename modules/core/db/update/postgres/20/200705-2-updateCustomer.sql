-- alter table ORDERSTORE_CUSTOMER add column OWNER_ID uuid ^
-- update ORDERSTORE_CUSTOMER set OWNER_ID = <default_value> ;
-- alter table ORDERSTORE_CUSTOMER alter column OWNER_ID set not null ;
alter table ORDERSTORE_CUSTOMER add column OWNER_ID uuid not null ;
