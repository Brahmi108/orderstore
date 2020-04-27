alter table ORDERSTORE_PRODUCT_SPECIFICATION rename column name to name__u20019 ;
alter table ORDERSTORE_PRODUCT_SPECIFICATION alter column name__u20019 drop not null ;
alter table ORDERSTORE_PRODUCT_SPECIFICATION add column SPEC_DATE date ^
update ORDERSTORE_PRODUCT_SPECIFICATION set SPEC_DATE = current_date where SPEC_DATE is null ;
alter table ORDERSTORE_PRODUCT_SPECIFICATION alter column SPEC_DATE set not null ;
alter table ORDERSTORE_PRODUCT_SPECIFICATION add column SPEC_NUMBER varchar(50) ^
update ORDERSTORE_PRODUCT_SPECIFICATION set SPEC_NUMBER = '' where SPEC_NUMBER is null ;
alter table ORDERSTORE_PRODUCT_SPECIFICATION alter column SPEC_NUMBER set not null ;
alter table ORDERSTORE_PRODUCT_SPECIFICATION add column COMMENT_ varchar(1000) ;
