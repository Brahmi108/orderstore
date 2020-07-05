create table ORDERSTORE_ADDRESS_TYPES (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    NAME varchar(200) not null,
    OWNER_ID uuid not null,
    CODE varchar(50) not null,
    --
    primary key (ID)
);