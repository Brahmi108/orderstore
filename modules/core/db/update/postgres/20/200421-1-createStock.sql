create table ORDERSTORE_STOCK (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    OWNER_ID uuid not null,
    ADDRESS varchar(2000),
    DEF boolean,
    --
    primary key (ID)
);