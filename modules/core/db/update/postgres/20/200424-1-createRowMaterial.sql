create table ORDERSTORE_ROW_MATERIAL (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer not null,
    --
    GOOD_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    PRODUCT_SPECIFICATION_ID uuid,
    --
    primary key (ID)
);