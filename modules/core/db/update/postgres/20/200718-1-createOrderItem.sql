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
);