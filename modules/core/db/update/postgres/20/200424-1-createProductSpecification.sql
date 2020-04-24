create table ORDERSTORE_PRODUCT_SPECIFICATION (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(255) not null,
    PRODUCT_ID uuid not null,
    QUANTITY double precision not null,
    UNIT_ID uuid not null,
    ACTIVE boolean,
    OWNER_ID uuid not null,
    INSTRUCTION text,
    --
    primary key (ID)
);