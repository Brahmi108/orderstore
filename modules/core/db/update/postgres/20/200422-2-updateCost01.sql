alter table ORDERSTORE_COST add constraint FK_ORDERSTORE_COST_ON_GOOD foreign key (GOOD_ID) references ORDERSTORE_GOODS(ID) on delete CASCADE;
