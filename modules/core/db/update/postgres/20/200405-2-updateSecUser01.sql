alter table SEC_USER add constraint FK_SEC_USER_ON_OWNER foreign key (OWNER_ID) references ORDERSTORE_OWNER(ID);
create index IDX_SEC_USER_ON_OWNER on SEC_USER (OWNER_ID);
