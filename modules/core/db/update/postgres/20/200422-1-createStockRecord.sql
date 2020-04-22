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
);