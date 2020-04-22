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
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
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
