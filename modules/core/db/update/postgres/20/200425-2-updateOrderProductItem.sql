alter table ORDERSTORE_ORDER_PRODUCT_ITEM rename column stock_productr_id to stock_productr_id__u82346 ;
alter table ORDERSTORE_ORDER_PRODUCT_ITEM alter column stock_productr_id__u82346 drop not null ;
alter table ORDERSTORE_ORDER_PRODUCT_ITEM drop constraint FK_ORDERSTORE_ORDER_PRODUCT_ITEM_ON_STOCK_PRODUCTR ;
drop index IDX_ORDERSTORE_ORDER_PRODUCT_ITEM_ON_STOCK_PRODUCTR ;
-- alter table ORDERSTORE_ORDER_PRODUCT_ITEM add column STOCK_PRODUCT_ID uuid ^
-- update ORDERSTORE_ORDER_PRODUCT_ITEM set STOCK_PRODUCT_ID = <default_value> ;
-- alter table ORDERSTORE_ORDER_PRODUCT_ITEM alter column STOCK_PRODUCT_ID set not null ;
alter table ORDERSTORE_ORDER_PRODUCT_ITEM add column STOCK_PRODUCT_ID uuid not null ;
