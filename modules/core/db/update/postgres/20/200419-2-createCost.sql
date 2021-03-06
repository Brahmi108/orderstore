alter table ORDERSTORE_COST add constraint FK_ORDERSTORE_COST_ON_COST_TYPE foreign key (COST_TYPE_ID) references ORDERSTORE_COST_TYPE(ID);
alter table ORDERSTORE_COST add constraint FK_ORDERSTORE_COST_ON_GOOD foreign key (GOOD_ID) references ORDERSTORE_GOODS(ID);
create index IDX_ORDERSTORE_COST_ON_COST_TYPE on ORDERSTORE_COST (COST_TYPE_ID);
create index IDX_ORDERSTORE_COST_ON_GOOD on ORDERSTORE_COST (GOOD_ID);
