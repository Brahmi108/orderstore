create table ORDERSTORE_ORDER_PRODUCT_MATERIAL (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    GOOD_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid,
    ORDER_PRODUCT_ID uuid,
    --
    primary key (ID)
);