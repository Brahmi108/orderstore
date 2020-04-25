alter table ORDERSTORE_ORDER_PRODUCT_MATERIAL add constraint FK_ORDERSTORE_ORDER_PRODUCT_MATERIAL_ON_GOOD foreign key (GOOD_ID) references ORDERSTORE_GOODS(ID);
alter table ORDERSTORE_ORDER_PRODUCT_MATERIAL add constraint FK_ORDERSTORE_ORDER_PRODUCT_MATERIAL_ON_UNIT foreign key (UNIT_ID) references ORDERSTORE_UNITS(ID);
alter table ORDERSTORE_ORDER_PRODUCT_MATERIAL add constraint FK_ORDERSTORE_ORDER_PRODUCT_MATERIAL_ON_ORDER_PRODUCT foreign key (ORDER_PRODUCT_ID) references ORDERSTORE_ORDER_PRODUCT(ID) on delete CASCADE;
create index IDX_ORDERSTORE_ORDER_PRODUCT_MATERIAL_ON_GOOD on ORDERSTORE_ORDER_PRODUCT_MATERIAL (GOOD_ID);
create index IDX_ORDERSTORE_ORDER_PRODUCT_MATERIAL_ON_UNIT on ORDERSTORE_ORDER_PRODUCT_MATERIAL (UNIT_ID);
create index IDX_ORDERSTORE_ORDER_PRODUCT_MATERIAL_ON_ORDER_PRODUCT on ORDERSTORE_ORDER_PRODUCT_MATERIAL (ORDER_PRODUCT_ID);
