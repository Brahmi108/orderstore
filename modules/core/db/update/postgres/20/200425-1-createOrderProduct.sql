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
    --
    primary key (ID)
);