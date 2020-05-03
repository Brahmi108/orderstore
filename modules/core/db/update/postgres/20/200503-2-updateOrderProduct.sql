alter table ORDERSTORE_ORDER_PRODUCT add column STOCK_MATERIALS_ID uuid ;
-- alter table ORDERSTORE_ORDER_PRODUCT add column STOCK_PRODUCT_ID uuid ^
-- update ORDERSTORE_ORDER_PRODUCT set STOCK_PRODUCT_ID = <default_value> ;
-- alter table ORDERSTORE_ORDER_PRODUCT alter column STOCK_PRODUCT_ID set not null ;
alter table ORDERSTORE_ORDER_PRODUCT add column STOCK_PRODUCT_ID uuid not null ;
