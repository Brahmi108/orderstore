alter table ORDERSTORE_GOOD_NAME_OPTION add constraint FK_ORDERSTORE_GOOD_NAME_OPTION_ON_GOODS foreign key (GOODS_ID) references ORDERSTORE_GOODS(ID) on delete CASCADE;
