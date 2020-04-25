create table ORDERSTORE_ORDER_PRODUCT_ITEM (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    STOCK_PRODUCTR_ID uuid not null,
    PRODUCT_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    SPECIFICATION_ID uuid,
    STOCK_MATERIALS_ID uuid,
    ORDER_PRODUCT_ID uuid,
    --
    primary key (ID)
);