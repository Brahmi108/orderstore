-- begin ORDERSTORE_GOOD_NAME_OPTION
create table ORDERSTORE_GOOD_NAME_OPTION (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    NAME varchar(500) not null,
    GOODS_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_GOOD_NAME_OPTION
-- begin ORDERSTORE_UNITS
create table ORDERSTORE_UNITS (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    OWNER_ID uuid not null,
    NAME varchar(255) not null,
    SHORT_NAME varchar(100) not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_UNITS
-- begin ORDERSTORE_OWNER
create table ORDERSTORE_OWNER (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    NAME varchar(500) not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_OWNER
-- begin ORDERSTORE_GOODS_GROUPS
create table ORDERSTORE_GOODS_GROUPS (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    OWNER_ID uuid not null,
    NAME varchar(255) not null,
    GCOMMENT varchar(255),
    --
    primary key (ID)
)^
-- end ORDERSTORE_GOODS_GROUPS
-- begin ORDERSTORE_GOODS
create table ORDERSTORE_GOODS (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    NAME varchar(500) not null,
    CODE varchar(100),
    UNIT_ID uuid not null,
    GROUP_ID uuid not null,
    OWNER_ID uuid not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_GOODS
-- begin SEC_USER
alter table SEC_USER add column OWNER_ID uuid ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'sec$User' where DTYPE is null ^
-- end SEC_USER
-- begin ORDERSTORE_COST_TYPE
create table ORDERSTORE_COST_TYPE (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    NAME varchar(255) not null,
    OWNER_ID uuid not null,
    DEF boolean,
    --
    primary key (ID)
)^
-- end ORDERSTORE_COST_TYPE
-- begin ORDERSTORE_COST
create table ORDERSTORE_COST (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    COST_TYPE_ID uuid not null,
    DATA_BEGIN date not null,
    DATE_END date,
    VALUE_ double precision not null,
    GOOD_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_COST
-- begin ORDERSTORE_STOCK
create table ORDERSTORE_STOCK (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(255) not null,
    OWNER_ID uuid not null,
    ADDRESS varchar(2000),
    DEF boolean,
    --
    primary key (ID)
)^
-- end ORDERSTORE_STOCK
-- begin ORDERSTORE_STOCK_RECORD
create table ORDERSTORE_STOCK_RECORD (
    ID uuid,
    VERSION integer not null,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    STOCK_MOVEMENT_ID uuid,
    NOTE varchar(1000),
    GOOD_ID uuid not null,
    PRICE double precision not null,
    QUANTITY double precision not null,
    TOTAL double precision not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_STOCK_RECORD
-- begin ORDERSTORE_STOCK_MOVEMENT
create table ORDERSTORE_STOCK_MOVEMENT (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    STOCK_ID uuid not null,
    DATE_FACT date not null,
    NUM varchar(255) not null,
    CREATE_TIME timestamp not null,
    PRIORITY bigint not null,
    INCOME boolean not null,
    NOTES varchar(1000),
    --
    primary key (ID)
)^
-- end ORDERSTORE_STOCK_MOVEMENT
-- begin ORDERSTORE_ROW_MATERIAL
create table ORDERSTORE_ROW_MATERIAL (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    GOOD_ID uuid not null,
    NOTE varchar(1000),
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    PRODUCT_SPECIFICATION_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ROW_MATERIAL
-- begin ORDERSTORE_PRODUCT_SPECIFICATION
create table ORDERSTORE_PRODUCT_SPECIFICATION (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    SPEC_NUMBER varchar(50) not null,
    SPEC_DATE date not null,
    PRODUCT_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    ACTIVE boolean,
    OWNER_ID uuid not null,
    INSTRUCTION text,
    COMMENT_ varchar(1000),
    --
    primary key (ID)
)^
-- end ORDERSTORE_PRODUCT_SPECIFICATION
-- begin ORDERSTORE_ORDER_PRODUCT_MATERIAL
create table ORDERSTORE_ORDER_PRODUCT_MATERIAL (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    GOOD_ID uuid not null,
    NOTE varchar(1000),
    QUANTITY double precision not null,
    UNIT_ID uuid,
    ORDER_PRODUCT_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ORDER_PRODUCT_MATERIAL
-- begin ORDERSTORE_ORDER_PRODUCT_ITEM
create table ORDERSTORE_ORDER_PRODUCT_ITEM (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    NOTE varchar(1000),
    PRODUCT_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    SPECIFICATION_ID uuid,
    ORDER_PRODUCT_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ORDER_PRODUCT_ITEM
-- begin ORDERSTORE_ORDER_PRODUCT
create table ORDERSTORE_ORDER_PRODUCT (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    NUM varchar(100) not null,
    ORDER_DATE date not null,
    ORDER_NOTE varchar(2000),
    READY_DATE date,
    OWNER_ID uuid not null,
    STOCK_PRODUCT_ID uuid not null,
    STOCK_MATERIALS_ID uuid,
    STATUS_ID uuid not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ORDER_PRODUCT
-- begin ORDERSTORE_STATUS_ENTITY_TYPES
create table ORDERSTORE_STATUS_ENTITY_TYPES (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(100) not null,
    ENTITY_TYPE varchar(100) not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_STATUS_ENTITY_TYPES
-- begin ORDERSTORE_STATUS_ENTITY
create table ORDERSTORE_STATUS_ENTITY (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    OWNER_ID uuid not null,
    NAME varchar(100) not null,
    ENTITY_TYPE_ID uuid not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_STATUS_ENTITY
-- begin ORDERSTORE_ADDRESS_TYPES
create table ORDERSTORE_ADDRESS_TYPES (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(200) not null,
    CODE varchar(50) not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ADDRESS_TYPES
-- begin ORDERSTORE_CONTACT_TYPE
create table ORDERSTORE_CONTACT_TYPE (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(200) not null,
    CODE varchar(50) not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_CONTACT_TYPE
-- begin ORDERSTORE_CONTACT
create table ORDERSTORE_CONTACT (
    ID uuid,
    VERSION integer not null,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    CONTACT varchar(200) not null,
    TYPE uuid not null,
    CUSTOMER_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_CONTACT
-- begin ORDERSTORE_ADDRESS
create table ORDERSTORE_ADDRESS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    ADDRESS varchar(2000) not null,
    ADDRESS_TYPE uuid not null,
    CUSTOMER_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ADDRESS
-- begin ORDERSTORE_CUSTOMER
create table ORDERSTORE_CUSTOMER (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(500) not null,
    OWNER_ID uuid not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_CUSTOMER
-- begin ORDERSTORE_ORDER
create table ORDERSTORE_ORDER (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    NUM varchar(100) not null,
    ORDER_DATE date not null,
    ORDER_NOTE varchar(2000),
    OWNER_ID uuid not null,
    CUSTOMER_ID uuid not null,
    STATUS_ID uuid not null,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ORDER
-- begin ORDERSTORE_ORDER_ITEM
create table ORDERSTORE_ORDER_ITEM (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    NOTE varchar(1000),
    PRODUCT_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    ORDER_ID uuid,
    --
    primary key (ID)
)^
-- end ORDERSTORE_ORDER_ITEM
