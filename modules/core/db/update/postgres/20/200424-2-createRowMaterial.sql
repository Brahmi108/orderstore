alter table ORDERSTORE_ROW_MATERIAL add constraint FK_ORDERSTORE_ROW_MATERIAL_ON_GOOD foreign key (GOOD_ID) references ORDERSTORE_GOODS(ID);
alter table ORDERSTORE_ROW_MATERIAL add constraint FK_ORDERSTORE_ROW_MATERIAL_ON_UNIT foreign key (UNIT_ID) references ORDERSTORE_UNITS(ID);
alter table ORDERSTORE_ROW_MATERIAL add constraint FK_ORDERSTORE_ROW_MATERIAL_ON_PRODUCT_SPECIFICATION foreign key (PRODUCT_SPECIFICATION_ID) references ORDERSTORE_PRODUCT_SPECIFICATION(ID);
create index IDX_ORDERSTORE_ROW_MATERIAL_ON_GOOD on ORDERSTORE_ROW_MATERIAL (GOOD_ID);
create index IDX_ORDERSTORE_ROW_MATERIAL_ON_UNIT on ORDERSTORE_ROW_MATERIAL (UNIT_ID);
create index IDX_ORDERSTORE_ROW_MATERIAL_ON_PRODUCT_SPECIFICATION on ORDERSTORE_ROW_MATERIAL (PRODUCT_SPECIFICATION_ID);
