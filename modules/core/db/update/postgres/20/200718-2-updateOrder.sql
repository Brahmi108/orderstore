-- alter table ORDERSTORE_ORDER add column CUSTOMER_ID uuid ^
-- update ORDERSTORE_ORDER set CUSTOMER_ID = <default_value> ;
-- alter table ORDERSTORE_ORDER alter column CUSTOMER_ID set not null ;
alter table ORDERSTORE_ORDER add column CUSTOMER_ID uuid not null ;
