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
);