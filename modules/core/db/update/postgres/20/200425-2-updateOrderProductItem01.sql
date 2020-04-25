alter table ORDERSTORE_ORDER_PRODUCT_ITEM add constraint FK_ORDERSTORE_ORDER_PRODUCT_ITEM_ON_STOCK_PRODUCT foreign key (STOCK_PRODUCT_ID) references ORDERSTORE_STOCK(ID);
create index IDX_ORDERSTORE_ORDER_PRODUCT_ITEM_ON_STOCK_PRODUCT on ORDERSTORE_ORDER_PRODUCT_ITEM (STOCK_PRODUCT_ID);