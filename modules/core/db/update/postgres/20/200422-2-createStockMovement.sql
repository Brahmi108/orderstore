alter table ORDERSTORE_STOCK_MOVEMENT add constraint FK_ORDERSTORE_STOCK_MOVEMENT_ON_STOCK foreign key (STOCK_ID) references ORDERSTORE_STOCK(ID);
create index IDX_ORDERSTORE_STOCK_MOVEMENT_ON_STOCK on ORDERSTORE_STOCK_MOVEMENT (STOCK_ID);
