create table ORDERSTORE_ADDRESS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    ADDRESS varchar(2000) not null,
    ADDRESS_TYPE uuid not null,
    --
    primary key (ID)
);