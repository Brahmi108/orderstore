-- alter table ORDERSTORE_ORDER_PRODUCT add column STATUS_ID uuid ^
-- update ORDERSTORE_ORDER_PRODUCT set STATUS_ID = <default_value> ;
-- alter table ORDERSTORE_ORDER_PRODUCT alter column STATUS_ID set not null ;
alter table ORDERSTORE_ORDER_PRODUCT add column STATUS_ID uuid not null ;
